import axios from 'axios';

const apiURL = 'http://csci5308vm16.research.cs.dal.ca:8080/api/test';

export async function executeTestCase(testCaseDetails) {
    const response = await axios.post(apiURL, testCaseDetails);
    return response.data;
}