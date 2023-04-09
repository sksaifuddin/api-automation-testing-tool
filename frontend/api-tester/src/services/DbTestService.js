import axios from 'axios';

const apiURL = 'http://csci5308vm16.research.cs.dal.ca:8080/api/test-client-db-connection';
const getClientDBCredentialsURL = 'http://csci5308vm16.research.cs.dal.ca:8080/api/get-client-db-credentials';

export async function testClientDBConnection(url, userName, password) {
    const response = await axios.post(apiURL, {
        "databaseUrl": url,
        "userName": userName,
        "password": password
    });
    return response.data;
}

export async function getClientDBCredentials() {
    return axios.get(getClientDBCredentialsURL);
}