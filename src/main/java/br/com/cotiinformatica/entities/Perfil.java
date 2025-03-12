package br.com.cotiinformatica.entities;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "tb_perfil")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Perfil {

	@Id
	@Column(name = "id")
	private UUID id;
	
	@Column(name = "nome", length = 25, nullable = false, unique = true)
	private String nome;
	
	@OneToMany(mappedBy = "perfil")
	private List<Usuario> usuarios;
	
}
