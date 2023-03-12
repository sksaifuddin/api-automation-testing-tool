import axios from 'axios';

const apiURL = 'api/test-client-db-connection';
const getClientDBCredentialsURL = '/api/get-client-db-credentials';

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