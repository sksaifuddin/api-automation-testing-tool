import axios from 'axios';

const getAllTestCasesURL = 'http://csci5308vm16.research.cs.dal.ca:8080/api/testcases/get';

export async function getAllTestCases() {
    const response = await axios.get(getAllTestCasesURL);
    console.log(response.data);
    return response.data;
}