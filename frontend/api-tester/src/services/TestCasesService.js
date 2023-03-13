import axios from 'axios';

const getAllTestCasesURL = 'api/testcases/get';

export async function getAllTestCases() {
    const response = await axios.get(getAllTestCasesURL);
    console.log(response.data);
    return response.data;
}