import React from "react";
import Header from "../Header/Header";
import Main from "../main/Main";
import Sidebar from "../Sidebar/Sidebar";

const Layout = (props) => {
    return <>
        <Header></Header>
        <Main></Main>
        <Sidebar></Sidebar>
    </>;
}

export default Layout;
