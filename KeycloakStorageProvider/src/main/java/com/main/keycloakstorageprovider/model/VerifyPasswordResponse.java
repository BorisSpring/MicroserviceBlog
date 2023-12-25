package com.main.keycloakstorageprovider.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VerifyPasswordResponse {

    private boolean isValid;
}
