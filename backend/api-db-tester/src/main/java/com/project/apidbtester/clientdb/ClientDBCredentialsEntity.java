package com.project.apidbtester.clientdb;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ClientDBCredentialsEntity is used to store client db credentials
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name="ClientDBCredentials")
public class ClientDBCredentialsEntity {
    @Id
    private Long databaseId;
    private String databaseUrl;
    private String userName;
    private String password;
}
