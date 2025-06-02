package br.com.codaedorme.pi.domain.api.cliente;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.codaedorme.pi.domain.api.cliente.enums.UserRole;
import br.com.codaedorme.pi.domain.api.endereco.Endereco;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "clientes")
public class Cliente implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cliente_id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String senha;

	@Column(nullable = false, unique = true, length = 11)
	@CPF
	private String cpf;

	@Column(nullable = false)
	private String nomeCompleto;

	@Column(nullable = false)
	private LocalDate dataNascimento;

	@Column(nullable = false)
	private String genero;

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)
	@JsonManagedReference
	private List<Endereco> enderecoEntrega;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "endereco_faturamento_id")
	private Endereco enderecoFaturamento;

	@Enumerated(EnumType.STRING)
	private UserRole role;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getNomeCompleto() {
		return nomeCompleto;
	}

	public void setNomeCompleto(String nomeCompleto) {
		this.nomeCompleto = nomeCompleto;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public List<Endereco> getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(List<Endereco> enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public Endereco getEnderecoFaturamento() {
		return enderecoFaturamento;
	}

	public void setEnderecoFaturamento(Endereco enderecoFaturamento) {
		this.enderecoFaturamento = enderecoFaturamento;
	}

	// @Override
	// public Collection<? extends GrantedAuthority> getAuthorities() {
	// if (this.role == UserRole.ADMIN)
	// return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new
	// SimpleGrantedAuthority("ROLE_USER"));
	// else
	// return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	// }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (this.role == UserRole.ADMIN) {
			return List.of(
					new SimpleGrantedAuthority("ROLE_ADMIN"),
					new SimpleGrantedAuthority("ROLE_USER"));
		} else if (this.role == UserRole.ESTOQUISTA) {
			return List.of(new SimpleGrantedAuthority("ROLE_ESTOQUISTA"));
		} else {
			return List.of(new SimpleGrantedAuthority("ROLE_USER"));
		}
	}

	@Override
	public String getPassword() {
		return senha;
	}

	@Override
	public String getUsername() {
		return email;
	}

}
