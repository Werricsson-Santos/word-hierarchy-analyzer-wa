import { useState } from 'react';
import { Button, Form as BootstrapForm, FormGroup, Label, Input, Alert, Row, Col, Container } from 'reactstrap';

interface WordAnalysis {
    depth: number;
    category: string;
    hierarchyCount: Record<string, number>;
    performAnalysis: Record<string, number>;
}

const Form: React.FC = () => {
    const [depth, setDepth] = useState<number | undefined>(undefined);
    const [verbose, setVerbose] = useState<boolean>(false);
    const [text, setText] = useState<string>('');
    const [response, setResponse] = useState<WordAnalysis>();
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const requestData = {
            depth,
            verbose,
            text,
        };

        try {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/word-analyzer/analyze?verbose=${verbose}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (!res.ok) {
                const errorData = await res.json()
                throw new Error(errorData.message || 'Network response was not ok');
            }

            const data = await res.json();
            console.log(data);
            setResponse(data);
            setError(null); 
        } catch (error: any) {
            if (error?.message === 'Failed to fetch') {
                setError('Falha ao buscar os dados no backend. O servidor está offline ou a URL está incorreta.');
            } else if (error.message.includes('Connection refused')) {
                setError('Conexão recusada. O servidor está offline ou a URL está incorreta')
            } else {
                setError('Error: ' && error.message);
            }
        }
    };

    const handleDownload = () => {
        if (response) {
            const blob = new Blob([JSON.stringify(response, null, 2)], { type: 'application/json' });
            const url = window.URL.createObjectURL(blob);
            const a = document.createElement('a');
            a.href = url;
            a.download = 'response.json';
            a.click();
            window.URL.revokeObjectURL(url);
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center vh-100"> 
            <BootstrapForm onSubmit={handleSubmit} className="w-50"> 
                <Row>
                    <Col xs="6"> 
                        <FormGroup>
                            <Label for="depth">Profundidade (opcional):</Label>
                            <Input
                                type="number"
                                id="depth"
                                value={depth || ''}
                                onChange={(e) => setDepth(e.target.value ? parseInt(e.target.value) : undefined)}
                                className="w-25"
                            />
                        </FormGroup>
                    </Col>
                    <Col xs="6" className="text-center">
                        <FormGroup check>
                            <Label for="verbose">
                                Resposta verbosa
                                <Input
                                    type="checkbox"
                                    id="verbose"
                                    checked={verbose}
                                    onChange={(e) => setVerbose(e.target.checked)}
                                    className="ml-2"
                                />
                            </Label>
                        </FormGroup>
                    </Col>
                </Row>
                <Row className="mt-3">
                    <Col>
                        <FormGroup>
                            <Label for="text">Texto:</Label>
                            <Input
                                type="textarea"
                                id="text"
                                value={text}
                                onChange={(e) => setText(e.target.value)}
                                required
                            />
                        </FormGroup>
                    </Col>
                </Row>
                <Row className="text-center">
                    <Col>
                        <Button type="submit">Analisar</Button>
                    </Col>
                </Row>
                {error && <Alert color="danger" className="mt-3">{error}</Alert>}
                {response && (
                    <div className="mt-3">
                        <h3>Resposta:</h3>
                        {response.hierarchyCount ? (
                            <ul>
                                {Object.entries(response.hierarchyCount).map(([category, count]) => (
                                    <li key={category}>
                                        <strong>{category}</strong>: {count} palavras
                                    </li>
                                ))}
                            </ul>
                        ) : (
                            <ul>
                                {Object.entries(response).map(([category, count]) => (
                                    <li key={category}>
                                        <strong>{category}</strong>: {count} palavra{count > 1 ? "s" : ""}
                                    </li>
                                ))}
                            </ul>
                        )}

                        {verbose && response.performAnalysis && (
                            <table className="table">
                                <thead>
                                    <tr>
                                        <th>Operação</th>
                                        <th>Tempo (ms)</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {Object.entries(response.performAnalysis).map(([operation, time]) => (
                                        <tr key={operation}>
                                            <td>{operation}</td>
                                            <td>{time} {time > 0 ? "ms" : ""}</td>
                                        </tr>
                                    ))}
                                </tbody>
                            </table>
                        )}
                        <Button onClick={handleDownload} className="bg-success mt-3">
                            Download JSON
                        </Button>
                    </div>
                )}
            </BootstrapForm>
        </Container>
    );
};

export default Form;