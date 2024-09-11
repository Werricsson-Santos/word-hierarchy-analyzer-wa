import Link from "next/link"
import { Nav, Navbar } from "reactstrap"

const Header = () => {
    return (
        <Navbar container="md" color="dark" dark>
            <Link href="/" className="navbar-brand">
                    Início
            </Link>
            <Nav className="flex-row" navbar>
                <Link href="/wordanalyze" className="nav-link me-2">
                        Word Analyze
                </Link>
                <Link href="/hierarchies" className="nav-link">
                        Hierarquias
                </Link>
            </Nav>
        </Navbar>
    )
}

export default Header