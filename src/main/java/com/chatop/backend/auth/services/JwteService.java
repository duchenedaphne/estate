package com.chatop.backend.auth.services;

import org.springframework.stereotype.Service;

import com.nimbusds.jose.EncryptionMethod;
import com.nimbusds.jose.JWEAlgorithm;
import com.nimbusds.jose.JWEHeader;
import com.nimbusds.jose.JWEObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.RSADecrypter;
import com.nimbusds.jose.crypto.RSAEncrypter;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.EncryptedJWT;
import com.nimbusds.jwt.SignedJWT;

@Service
public class JwteService {
    
    RSAKey publicJWK;
    RSAKey keyPublicJWK;

    public JwteService() {
		try {
			this.publicJWK = new RSAKeyGenerator(4096).keyID("456").keyUse(KeyUse.ENCRYPTION).generate();
			this.keyPublicJWK = publicJWK.toPublicJWK();

		} catch (Exception e) {
			e.printStackTrace();
		}
    }

    public String encode(String jwts) {
	try {
	    SignedJWT parsed = SignedJWT.parse(jwts);            
	    JWEObject jweObject = new JWEObject(
			new JWEHeader.Builder(
				JWEAlgorithm.RSA_OAEP_256, 
				EncryptionMethod.A256GCM
			)
			.contentType(jwts)
			.build(),
			new Payload(parsed)
	    );
	    jweObject.encrypt(new RSAEncrypter(this.keyPublicJWK));            
	    String jweString = jweObject.serialize();
	    return jweString;

	} catch (Exception e) {
	    e.printStackTrace();
	}
	return "";
    }
	
    public String decode(String token) {
		try {
			EncryptedJWT parse = EncryptedJWT.parse(token);
			RSADecrypter decrypte = new RSADecrypter(this.publicJWK);
			parse.decrypt(decrypte);
			SignedJWT signedJWT = parse.getPayload().toSignedJWT();
			return signedJWT.serialize();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
    }
}
