package appUtilities;

import java.util.concurrent.TimeUnit;

import appShared.Shared;

import com.kuka.roboticsAPI.applicationModel.IApplicationData;
import com.kuka.roboticsAPI.conditionModel.ICondition;
import com.kuka.roboticsAPI.geometricModel.Frame;
import com.kuka.roboticsAPI.geometricModel.ObjectFrame;

/**
 * Clase con distintas funciones para utilizar en la secuencia.
 */
public final class Utils
{
	/**
	 * Lee los valores de las variables del ProcessData.
	 */
	public static void readProcessData()
	{
		readProcessData(Shared.appData);
	}
	
	/**
	 * Lee los valores de las variables del ProcessData especificado.
	 * @param appData El ProcessData del que se leer�
	 */
	public static void readProcessData(IApplicationData appData)
	{
		try
		{
			// Mostrar el c�digo de conector seleccionado desde la pantalla
			Shared.COD_CON = Shared.iExtension.getCodCon();
			if (Shared.COD_CON == 1)
			{
				appData.getProcessData("codCon").setValue("Conector redondo");
			}
			else if (Shared.COD_CON == 2)
			{
				appData.getProcessData("codCon").setValue("Conector cuadrado");
			}
			else
			{
				appData.getProcessData("codCon").setValue(" " + Shared.COD_CON);	
			}
			// Mostrar el c�digo de motor le�do desde el PLC
			Shared.COD_MOT = Shared.iExtension.getCodMot();
			appData.getProcessData("codMot").setValue("0");
		}
		catch (Exception e)
		{
			
		}
		
		// Activar/Desactivar opciones para la ejecuci�n del programa
		Shared.ACT_DEBUG = (Boolean)(appData.getProcessData("activarDebug").getValue());
		Shared.ACT_REFERENCIADO = (Boolean)(appData.getProcessData("activarReferenciado").getValue());
		if ((Boolean)(appData.getProcessData("resetTiemposCiclo").getValue()) == true)
		{
			Shared.numMotores = 0;
			Shared.sumTiempo = 0.0;
			appData.getProcessData("tiempoCiclo").setValue(0.0);
			appData.getProcessData("tiempoCicloAvg").setValue(0.0);
			appData.getProcessData("intentoMedirNivel").setValue(0);
			appData.getProcessData("resetTiemposCiclo").setValue(false);
		}
	}
	
	
	/**
	 * Si est� activo el modo debug desde el ProcessData, muestra mensajes del
	 * estado/movimientos del robot.
	 * @param mensaje El mensaje que aparece en pantalla
	 */
	public static void debug(String mensaje)
	{
		if (Shared.ACT_DEBUG && Shared.appLogger!=null)
		{
			Shared.appLogger.info(mensaje);
		}
	}
	
	/**
	 * Funci�n que muestra en el ProcessData el �ltimo punto al que se movi� el robot.
	 * @param frame Texto del punto en el que est� el robot
	 */
	public static void debugUltimoPunto(String frame)
	{
		Shared.appData.getProcessData("ultimoFrame").setValue("" + frame);
	}
	
	/**
	 * Funci�n que muestra en el ProcessData la posici�n actual del robot.
	 */
	public static void debugPosAct()
	{
		Shared.appData.getProcessData("posAct").setValue(Math.round(Shared.posAct.getX())
													+ ", " + Math.round(Shared.posAct.getY())
													+ ", " + Math.round(Shared.posAct.getZ()));
	}
	
	/**
	 * Funci�n para mostrar en el Logger las coordenadas de la posici�n indicada.
	 * @param String Frame del que se quieren mostrar las coordenadas
	 */
	public static void debugFrameDest(String frame)
	{
		Shared.appLogger.info("Frame " + frame + "\nX: " + Math.round(Shared.appData.getFrame(frame).getX())
							+ ", Y: " + Math.round(Shared.appData.getFrame(frame).getY())
							+ ", Z: " + Math.round(Shared.appData.getFrame(frame).getZ())
							+ ", A: " + Math.round(Math.toDegrees(Shared.appData.getFrame(frame).getAlphaRad()))
							+ ", B: " + Math.round(Math.toDegrees(Shared.appData.getFrame(frame).getBetaRad()))
							+ ", C: " + Math.round(Math.toDegrees(Shared.appData.getFrame(frame).getGammaRad())));
	}
	
	/**
	 * Comprueba las coordenadas cartesianas de la posici�n actual del robot utilizando
	 * como referencia el mundo.
	 * @return Frame con las coordenadas actuales
	 */
	public static Frame pos_act()
	{
		return Shared.robot.getCurrentCartesianPosition(Shared.robot.getFlange());
	}
	
	/**
	 * Comprueba las coordenadas cartesianas de la posici�n actual del robot utilizando
	 * como referencia la base especificada.
	 * @param base Base para tomar como referencia
	 * @param tcpTool Herramienta de referencia para la posici�n
	 * @return Frame con las coordenadas actuales
	 */
	public static Frame pos_act(ObjectFrame tcpTool, ObjectFrame base)
	{
		return Shared.robot.getPositionInformation(tcpTool, base).getCurrentCartesianPosition();
	}
	
	/**
	 * Comprueba si el robot est� en la posici�n indicada, con un error dependiente
	 * de la precisi�n indicada.
	 * @param frame String con el nombre del Frame que se quiere comprobar
	 * @param tcpTool Herramienta de referencia para la posici�n
	 * @param precision Double con la precisi�n del punto
	 * @return True si el robot est� en esa posici�n, False en caso contrario
	 */
	public static boolean isInFrame(ObjectFrame tcpTool, String frame, double precision)
	{
		return (pos_act(tcpTool, Shared.appData.getFrame(Shared.WORLD)).distanceTo(Shared.appData.getFrame(frame))<precision);
	}
	
	
	/**
	 * Espera a que se cumpla una determinada condici�n. Una vez que se cumpla, el programa
	 * contin�a su ejecuci�n.
	 * @param condicion Condici�n que debe cumplirse
	 * @param mensajeCiclico Mensaje que aparecer� mientras no se cumpla "condicion"
	 * @param segundos Tiempo (en segundos) para volver a mostrar el mensaje (0 para no mostrarlo)
	 */
	public static void waitFor(ICondition condicion, String mensajeCiclico, int segundos)
	{
		boolean condici�nOK = false;
		int i = 0;
		
		do
		{
			condici�nOK = Shared.appObserver.waitFor(condicion, 10, TimeUnit.MILLISECONDS);

			i++;
			if (!condici�nOK && i>=(segundos*100) && segundos>0)
			{
				if (Shared.ACT_DEBUG)
				{
					Shared.appLogger.info(mensajeCiclico);
				}
				i = 0;
			}
		}while(!condici�nOK);
	}
	
	/**
	 * Resetea las salidas del robot y prepara el robot para iniciar la secuencia.
	 */
	public static void resetearSalidas()
	{
		Shared.ioFlange.setCerrarPinza(false);
		Shared.ioFlange.setActivarVacio(false);
		
		Shared.oExtension.setErrorPrograma(false);
		Shared.oExtension.setReposNoOK(false);
		Shared.oExtension.setBusqCuadNoOK(false);
		Shared.oExtension.setPosVacio(false);
		Shared.oExtension.setPosNivel(false);
		Shared.oExtension.setPosFinNivel(false);
		Shared.oExtension.setPosInhibido(false);
	}
}
