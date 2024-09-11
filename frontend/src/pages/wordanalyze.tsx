import Header from "@/components/Header";
import { NextPage } from "next";
import Head from "next/head";

const WordAnalyze: NextPage = () => {
    return (
        <>
        <Head>
            <title>Insira seu texto para analise</title>
            <meta name="description" content="Conheça todos os WiP" />
            <link rel="icon" href="/favicon.ico" />
        </Head>
        
        <Header />

        <h1>
            WiP
        </h1>
        </>
    )
}

export default WordAnalyze