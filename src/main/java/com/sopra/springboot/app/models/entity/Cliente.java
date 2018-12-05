package com.sopra.springboot.app.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="clientes")
public class Cliente implements Serializable 
{
	private static final long serialVersionUID = 1L; //Obliatorio por ser un Serializable

	@Id //Indica que es la clave primaria
	@GeneratedValue(strategy = GenerationType.IDENTITY) //Le da un valor inicial a la variable
	private Long id;
	
	@NotEmpty
	private String nombre; //Aqui tambien tendriamos que poner el @Column pero al ser una @Entity,
						   //detecta por defecto que son columnas
	@NotEmpty
	private String apellido;
	@NotEmpty
	@Email
	private String email;
	
	@NotNull
	@Column(name="create_at")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern="dd-MM-yy")
	private Date createAt;
	
//	@PrePersist //Esto sirve por si queremos que se añada la fecha de manera automatica
//	public void prePersist()
//	{
//		createAt = new Date();
//	}
	
	public Long getId() 
	{
		return id;
	}

	public void setId(Long id) 
	{
		this.id = id;
	}
	
	
	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}


	public String getApellido() 
	{
		return apellido;
	}

	public void setApellido(String apellido) 
	{
		this.apellido = apellido;
	}


	public String getEmail() 
	{
		return email;
	}

	public void setEmail(String email) 
	{
		this.email = email;
	}

	
	public Date getCreateAt() 
	{
		return createAt;
	}

	public void setCreateAt(Date createAt) 
	{
		this.createAt = createAt;
	}
}
