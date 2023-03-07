import axios from 'axios';

const apiURL = 'api/test-client-db-connection';

export async function testClientDBConnection(url, userName, password) {
    const response = await axios.post(apiURL, {
        "databaseUrl": url,
        "userName": userName,
        "password": password
    });
    return response.data;
}