import React, { useEffect, useState } from "react";
import { getClientDBMetadata } from "../../../../services/ClientDBMetaData";
import AddTestCase from "../../components/AddTestCase";
// eslint-disable-next-line no-unused-vars
import styles from "./AddTestCaseContainer.scss"

const AddTestCaseContainer = () => {
  const [clientDBMetaData, setClientDBMetaData] = useState({});

  useEffect(() => {
      getClientDBMetadata().then((data) => {
        console.log('after get call', setClientDBMetaData(data));
      });
  }, [])
  return (
    <>
      <div className="add-test-case-container">
        <AddTestCase clientDBMetaData={{...clientDBMetaData}}></AddTestCase>
      </div>
    </>
  );
};

export default AddTestCaseContainer;
