import { useState } from 'react';
import { Button, Form as BootstrapForm, FormGroup, Label, Input, Alert, Row, Col, Container } from 'reactstrap';

const Form: React.FC = () => {
    const [depth, setDepth] = useState<number | undefined>(undefined);
    const [verbose, setVerbose] = useState<boolean>(false);
    const [text, setText] = useState<string>('');
    const [response, setResponse] = useState<any>(null);
    const [error, setError] = useState<string | null>(null);

    const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
        e.preventDefault();

        const requestData = {
            depth,
            verbose,
            text,
        };

        try {
            const res = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/word-analyzer/analyze`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(requestData),
            });

            if (!res.ok) {
                throw new Error('Network response was not ok');
            }

            const data = await res.json();
            setResponse(data);
            setError(null); // Limpar erros anteriores
        } catch (error) {
            setError('Failed to fetch data from the backend.');
            console.error('Error:', error);
        }
    };

    return (
        <Container className="d-flex justify-content-center align-items-center vh-100"> {/* Container centralizado */}
            <BootstrapForm onSubmit={handleSubmit} className="w-50"> {/* Largura ajustada */}
                <Row>
                    <Col xs="6"> {/* Campo depth alinhado à esquerda */}
                        <FormGroup>
                            <Label for="depth">Depth (opcional):</Label>
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
                                Verbose Response:
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
                            <Label for="text">Text:</Label>
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
                        <Button type="submit">Submit</Button>
                    </Col>
                </Row>
                {error && <Alert color="danger" className="mt-3">{error}</Alert>}
                {response && (
                    <div className="mt-3">
                        <h3>Response:</h3>
                        <pre>{JSON.stringify(response, null, 2)}</pre>
                    </div>
                )}
            </BootstrapForm>
        </Container>
    );
};

export default Form;