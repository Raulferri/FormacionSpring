package com.sopra.springboot.app.util.paginater;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

public class PageRender<T> 
{
	private String url;
	private Page<T> page;
	private int totalPaginas;
	private int paginaActual;
	private int numElementosPorPagina;
	private List<PageItem> paginas;
	
	public PageRender(String url, Page<T> page)
	{
		int desde, hasta;
		
		this.url = url;
		this.page = page;
		this.paginas = new ArrayList<PageItem>();
		
		numElementosPorPagina = page.getSize();
		totalPaginas = page.getTotalPages();
		paginaActual = page.getNumber() + 1;
		
		if(totalPaginas <= numElementosPorPagina)
		{
			desde = 1;
			hasta = totalPaginas;
		}
		else
		{
			if(paginaActual <= numElementosPorPagina / 2)
			{
				desde = 1;
				hasta = numElementosPorPagina;
			}
			else if(paginaActual >= totalPaginas - numElementosPorPagina / 2)
			{
				desde = totalPaginas - numElementosPorPagina + 1;
				hasta = numElementosPorPagina;
			}
			else
			{
				desde = paginaActual - numElementosPorPagina / 2;
				hasta = numElementosPorPagina;
			}
		}
		
		for(int i = 0; i < hasta; ++i)
		{
			paginas.add(new PageItem(desde + i, paginaActual == desde + i));
		}
	}

	public String getUrl() 
	{
		return url;
	}

	public int getTotalPaginas() 
	{
		return totalPaginas;
	}

	public int getPaginaActual() 
	{
		return paginaActual;
	}

	public List<PageItem> getPaginas() 
	{
		return paginas;
	}
	
	public boolean isFirst()
	{
		return page.isFirst();
	}
	
	public boolean isLast()
	{
		return page.isLast();
	}
	
	public boolean isHashNext()
	{
		return page.hasNext();
	}
	
	public boolean isHashPrevious()
	{
		return page.hasPrevious();
	}
}
