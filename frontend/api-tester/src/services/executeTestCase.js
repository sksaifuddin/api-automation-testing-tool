import axios from 'axios';

const apiURL = 'api/test';

export async function executeTestCase(testCaseDetails) {
    const response = await axios.post(apiURL, testCaseDetails);
    return response.data;
}