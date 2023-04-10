import React from "react";
// eslint-disable-next-line no-unused-vars
import style from './Sidebar.style.scss';
import TestCases from "../../TestCases/testcases";
import DbConnectionTest from "../../db-connection-test/DbConnectionTest/DbConnectionTest";

const Sidebar = (props) => {
    return <>
        <div className="sidebarContainer">
           <DbConnectionTest></DbConnectionTest>
           <TestCases></TestCases>
        </div>
    </>;
}

export default Sidebar;
