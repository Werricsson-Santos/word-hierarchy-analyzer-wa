import Header from "@/components/Header";
import { GetServerSideProps, NextPage } from "next";
import Head from "next/head";
import Link from "next/link";
import { useEffect, useState } from "react";
import { Button, Container, Row, Col } from "reactstrap";

interface ApiResponse {
    id: string
    category: string
    subcategories: Object
}


const Hierarchies: NextPage = () => {
    const [clientSideData, setClienteSideData ] = useState<ApiResponse[] | undefined>()

    useEffect(() => {
        fetchData();
    }, [])

    const fetchData = async () => {
        try {
            const response = await fetch(`${process.env.NEXT_PUBLIC_API_URL}/hierarchies`);

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            const data = await response.json();
            console.log(data);
            setClienteSideData(data);
        } catch (error) {
            console.error("Fetch error: ", error);
        }
    }

    const renderSubcategories = (subcategories: { [key: string]: any }) => {
        return (
            <ul>
            {Object.entries(subcategories).map(([key, value]) => (
                <li key={key}>
                <strong>{key}</strong>
                {Array.isArray(value) ? (
                    // Se o valor for um array (lista de palavras)
                    <ul>
                    {value.map((item: string, index: number) => (
                        <li key={index}>{item}</li>
                    ))}
                    </ul>
                ) : (
                    // Se o valor for outro objeto (mais subcategorias)
                    renderSubcategories(value)
                )}
                </li>
            ))}
            </ul>
        );
    };

    return (
        <>
            <Head>
                <title>Hierarquia de palavras</title>
                <meta name="description" content="Hierarquia de palavras e subcategorias" />
                <link rel="icon" href="/favicon.ico" />
            </Head>

            <Header />
            
            <Container className="my-5">
                <h1 className="text-center flex-grow-1">Hierarquia de Palavras</h1>

            <div className="d-flex justify-content-end">
                <Link href="/wordanalyze">
                    <Button>
                        Analise uma frase
                    </Button>
                </Link>
            </div>
            </Container>
                

            <Container className="bg-dark text-white p-3 rounded mb-3 d-flex justify-content-center" style={{ maxHeight: '500px', overflowY: 'auto' }}>
                {clientSideData ? (
                    <ul>
                        {clientSideData.map((item) => (
                            <li key={item.category}>
                                <strong>{item.category}</strong>
                                {renderSubcategories(item.subcategories)}
                            </li>
                        ))}
                    </ul>
                ) : (
                    <p>Loading...</p>
                )}
            </Container>
        </>
    );
}

export default Hierarchies;