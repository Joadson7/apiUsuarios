package br.com.cotiinformatica.components;

import java.util.Date;

import org.springframework.stereotype.Component;

import br.com.cotiinformatica.entities.Usuario;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenComponent {

	
	public String getToken(Usuario usuario) {
		
	var secretKey = "9658011f-94ec-49e6-8602-f517a94fc6bb";
		
		return Jwts.builder()
               .setSubject(usuario.getEmail())
               .claim("perfil", usuario.getPerfil().getNome()) 
               .setIssuedAt(new Date()) 
               .setExpiration(new Date(System.currentTimeMillis() + 1800000))
               .signWith(SignatureAlgorithm.HS256, secretKey) 
               .compact();
	}
	
	
	
}

