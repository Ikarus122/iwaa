package appTools;

import appShared.Shared;

import com.kuka.roboticsAPI.geometricModel.ObjectFrame;
import com.kuka.roboticsAPI.geometricModel.Tool;

/**
 * Clase para definir la herramienta que llevará el robot.
 */
public class Garra
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	private Tool tool;		// Tipo de herramienta
	
	
	/******************************************************************************
	 * Funciones
	 *****************************************************************************/
	/**
	 * Constructor de la clase Garra, le asigna un herramienta usando una plantilla.
	 * @param template Plantilla de la garra que se utilizará
	 */
	public Garra(String template)
	{
		this.tool = Shared.appData.createFromTemplate(template);
	}
	
	/**
	 * Devuelve la herramienta actual asociada al robot.
	 * @return Herramienta actual del robot
	 */
	public Tool getTool()
	{
		return tool;
	}

	/**
	 * Modifica la herramienta actual del robot.
	 * @param tool Herramienta nueva para el robot
	 */
	public void setTool(Tool tool)
	{
		this.tool = tool;
	}
	
	/**
	 * Asocia la herramienta al robot.
	 * @param frame Frame de la herramienta que se usará
	 */
	public void attachTo(ObjectFrame frame)
	{
		tool.attachTo(frame);
	}
	
	/**
	 * Desasocia la garra del robot.
	 */
	public void detach()
	{
		tool.detach();
	}
	
	/**
	 * Devuelve el frame actual de la herramienta.
	 * @param path Nombre de la herramienta
	 * @return Frame de la herramienta actual
	 */
	public ObjectFrame getFrame(String path)
	{
		return tool.getFrame(path);
	}
	
	/**
	 * Devuelve el estado de la pinza.
	 * @return True si la pinza está abierta, False en caso contrario
	 */
	public Boolean isPinzaAbierta()
	{
		return Shared.ioFlange.getPinzaAbierta();
	}
	
	/**
	 * Abre la pinza si no está abierta.
	 */
	public void abrirPinza()
	{
		try
		{
			if (!isPinzaAbierta())
			{
				Shared.ioFlange.setCerrarPinza(false);
			}
		}
		catch (Exception e)
		{
			if (Shared.appRun)
			{
				Shared.appLogger.error("Error acceso a las IOs.\n" + e.toString());
			}
		}
	}
	
	/**
	 * Cierra la pinza si está abierta.
	 */
	public void cerrarPinza()
	{
		try
		{
			if (isPinzaAbierta())
			{
				Shared.ioFlange.setCerrarPinza(true);
			}
		}
		catch (Exception e)
		{
			if (Shared.appRun)
			{
				Shared.appLogger.error("Error acceso a las IOs.\n" + e.toString());
			}
		}
	}
	
	/**
	 * Devuelve el estado del vacío.
	 * @return True si el vacío está activado, False en caso contrario
	 */
	public Boolean isActVacio()
	{
		return !Shared.ioFlange.getVacioDesactivado();
	}
	
	/**
	 * Activa el vacío si está desactivado.
	 */
	public void actVacio()
	{
		try
		{
			if (!isActVacio())
			{
				Shared.ioFlange.setActivarVacio(true);
			}
		}
		catch (Exception e)
		{
			if (Shared.appRun)
			{
				Shared.appLogger.error("Error acceso a las IOs.\n" + e.toString());
			}
		}
	}
	
	/**
	 * Desactiva el vacío si está activado.
	 */
	public void desVacio()
	{
		try
		{
			if (isActVacio())
			{
				Shared.ioFlange.setActivarVacio(false);
			}
		}
		catch (Exception e)
		{
			if (Shared.appRun)
			{
				Shared.appLogger.error("Error acceso a las IOs.\n" + e.toString());
			}
		}
	}
}
