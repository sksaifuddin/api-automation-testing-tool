import React from "react";
// eslint-disable-next-line no-unused-vars
import style from './Sidebar.style.scss';
import DbConnectionTest from "../../db-connection-test/DbConnectionTest/DbConnectionTest";

const Sidebar = (props) => {
    return <>
        <div className="sidebarContainer">
           <DbConnectionTest></DbConnectionTest>
        </div>
    </>;
}

export default Sidebar;
