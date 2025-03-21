package appRenault;

import java.util.concurrent.TimeUnit;

import appShared.Shared;
import static appUtilities.Utils.*;

import com.kuka.generated.ioAccess.InputsProfinetIOGroup;
import com.kuka.generated.ioAccess.MediaFlangeIOGroup;
import com.kuka.generated.ioAccess.OutputsProfinetIOGroup;
import com.kuka.roboticsAPI.applicationModel.tasks.CycleBehavior;
import com.kuka.roboticsAPI.applicationModel.tasks.RoboticsAPICyclicBackgroundTask;
import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.controllerModel.recovery.IRecovery;
import com.kuka.roboticsAPI.controllerModel.sunrise.SunriseSafetyState.EnablingDeviceState;
import com.kuka.roboticsAPI.controllerModel.sunrise.positionMastering.PositionMastering;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.uiModel.userKeys.*;

/**
 * Clase que se ejecuta en el Background. Comprueba el estado de entradas, salidas o posición actual.
 */
public class BackgroundTask extends RoboticsAPICyclicBackgroundTask
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	private LBR robot;
	private Controller controladora;
	private int ms50, ms100, ms200, ms500, ms1000, ms2000;		// Contadores
	private Boolean bitVida, bitVidaAnt, bitVidaFallo;			// Variables para comprobar si se pierde el Bit de Vida con el PLC para dar un fallo
	private int bitVidaCnt = 0;									// Contador para la comprobación del Bit de Vida
	private IRecovery reposicionar;								// Variable para comprobar si el robot necesita reposicionar por haber sido movido manualmente
	
	// Botones para la barra de la garra
	protected IUserKey btnAbrirPinza;
	protected IUserKey btnCerrarPinza;
	protected IUserKey btnActVacio;
	protected IUserKey btnDesVacio;

	
	/******************************************************************************
     * Ejecución
     *****************************************************************************/
	@Override
	public void dispose()
	{
		super.dispose();
	}
	
	@Override
	public void initialize()
	{
		controladora = getController("KUKA_Sunrise_Cabinet_1");
		robot = (LBR) getRobot(controladora, "LBR_iiwa_14_R820_1");
		ms50 = 0; ms100 = 0; ms200 = 0; ms500 = 0; ms1000 = 0;
		
		// Inicializar entradas y salidas
		setPLCIOs();
		
		// Barra de botones para la garra
		createKeyBarGarra();
		
		// Llama a runCyclic cada 10 milisegundos
		initializeCyclic(0, 10, TimeUnit.MILLISECONDS,	CycleBehavior.BestEffort);
	}

	@Override
	public void runCyclic()
	{
		// Para evitar hacer todas las comprobaciones a la vez, se utilizan contadores
		// Estos permiten separar las acciones del Background en bloques temporizados según sea necesario
		ms50++; ms100++; ms200++; ms500++; ms1000++; ms2000++;
		
		try
		{	
			task10ms();
		}
		catch (Exception e)
		{
			
		}
		if (ms50 >= 5)
		{
			try
			{
				task50ms();
				ms50 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
		if (ms100 >= 10)
		{
			try
			{
				task100ms();
				ms100 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
		if (ms200 >= 20)
		{
			try
			{
				task200ms();
				ms200 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
		if (ms500 >= 50)
		{
			try
			{
				task500ms();
				ms500 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
		if (ms1000 >= 100)
		{
			try
			{
				task1000ms();
				ms1000 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
		if (ms2000 >= 200)
		{
			try
			{
				task2000ms();
				ms2000 = 0;
			}		
			catch (Exception e)
			{
				
			}
		}
	}
	
	
	/******************************************************************************
	 * Funciones
	 *****************************************************************************/
	/**
	 * Inicializa las entradas y salidas del PLC, tanto las del flange como las E/S estándar.
	 * Lo reintenta hasta que consigue iniciarlas todas correctamente.
	 */
	private void setPLCIOs()
	{
		do
		{
			try
			{
				Shared.ioFlange = new MediaFlangeIOGroup(controladora);
				Shared.iExtension = new InputsProfinetIOGroup(controladora);
				Shared.oExtension = new OutputsProfinetIOGroup(controladora);
				break;
			}
			catch (Exception e)
			{
				continue;
			}
		}while(true);
	}
	
	/**
	 * Crea la barra de botones para la garra. Incluye botones para abrir/cerrar pinza y
	 * activar/desactivar vacío.
	 */
	private void createKeyBarGarra()
	{
		IUserKeyBar barraBotonesGarra = getApplicationUI().createUserKeyBar("Garra");

		IUserKeyListener abrirPinzaListener = new IUserKeyListener()
		{
			public void onKeyEvent(IUserKey key, UserKeyEvent event)
			{
				if (!robot.getOperationMode().isModeAuto())
				{
					try
					{
						abrirPinza();
						btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
						btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
					}
					catch(Exception e)
					{
						
					}
				}
			}  
		};

		IUserKeyListener cerrarPinzaListener = new IUserKeyListener()
		{
			public void onKeyEvent(IUserKey key, UserKeyEvent event)
			{
				if (!robot.getOperationMode().isModeAuto())
				{
					try
					{
						cerrarPinza();
						btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
						btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		};
		
		IUserKeyListener actVacioListener = new IUserKeyListener()
		{
			public void onKeyEvent(IUserKey key, UserKeyEvent event)
			{
				if (!robot.getOperationMode().isModeAuto())
				{
					try
					{
						actVacio();
						btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
						btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
					}
					catch(Exception e)
					{
						
					}
				}
			}  
		};

		IUserKeyListener desVacioListener = new IUserKeyListener()
		{
			public void onKeyEvent(IUserKey key, UserKeyEvent event)
			{
				if (!robot.getOperationMode().isModeAuto())
				{
					try
					{
						desVacio();
						btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
						btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
					}
					catch(Exception e)
					{
						
					}
				}
			}
		};
		
		btnAbrirPinza = barraBotonesGarra.addUserKey(0, abrirPinzaListener, true);
		btnCerrarPinza = barraBotonesGarra.addUserKey(1, cerrarPinzaListener, true);
		btnActVacio = barraBotonesGarra.addUserKey(2, actVacioListener, true);
		btnDesVacio = barraBotonesGarra.addUserKey(3, desVacioListener, true);
		
		btnAbrirPinza.setText(UserKeyAlignment.BottomMiddle, "Abrir");
		btnCerrarPinza.setText(UserKeyAlignment.BottomMiddle, "Cerrar");
		btnActVacio.setText(UserKeyAlignment.Middle, "Act");
		btnActVacio.setText(UserKeyAlignment.BottomMiddle, "Vacio");
		btnDesVacio.setText(UserKeyAlignment.Middle, "Des");
		btnDesVacio.setText(UserKeyAlignment.BottomMiddle, "Vacio");
		
		btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
		btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
		btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
		btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);

		barraBotonesGarra.publish();
	}
	
	/**
	 * Abre la pinza del robot.
	 */
	private void abrirPinza()
	{
		Shared.ioFlange.setCerrarPinza(false);
	}

	/**
	 * Cierra la pinza del robot.
	 */
	private void cerrarPinza()
	{
		Shared.ioFlange.setCerrarPinza(true);
	}
	
	/**
	 * Comprueba si la pinza está abierta.
	 * @return True si la pinza está abierta, False en caso contrario
	 */
	private boolean isPinzaAbierta()
	{
		if (Shared.ioFlange.getPinzaAbierta())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Comprueba si la pinza está cerrada.
	 * @return True si la pinza está cerrada, False en caso contrario
	 */
	private boolean isPinzaCerrada()
	{
		if (!Shared.ioFlange.getPinzaAbierta())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Activa el vacío de la pinza.
	 */
	private void actVacio()
	{
		Shared.ioFlange.setActivarVacio(true);
	}

	/**
	 * Desactiva el vacío de la pinza.
	 */
	private void desVacio()
	{
		Shared.ioFlange.setActivarVacio(false);
	}
	
	/**
	 * Comprueba si el vacío está activado.
	 * @return True si el vacío está activo, False en caso contrario
	 */
	private boolean isActVacio()
	{
		if (!Shared.ioFlange.getVacioDesactivado())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Comprueba si el vacío está desactivado.
	 * @return True si el vacío está desactivado, False en caso contrario
	 */
	private boolean isDesVacio()
	{
		if (Shared.ioFlange.getVacioDesactivado())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Comprueba el valor del Bit de Vida del PLC para saber si no ha cambiado durante un tiempo
	 * y detectar un fallo en las comunicaciones.
	 */
	private void checkLifeBit()
	{
		// Se comprueba el valor actual del Bit de Vida del PLC
		bitVida = Shared.iExtension.getLifeBit();
		
		// Si el valor actual es distinto del anterior, se está recibiendo correctamente
		// Si no es distinto, se incrementa el contador
		if (bitVida != bitVidaAnt)
		{
			bitVidaCnt = 0;
			bitVidaFallo = false;
		}
		else
		{
			bitVidaCnt++;
		}
		// Se iguala el valor anterior con el actual para volver a comparar en el siguiente ciclo
		bitVidaAnt = bitVida;
		
		// Si el contador vale 10 y no está activo el fallo del Bit de Vida, quiere decir que el valor
		// del Bit de Vida no ha cambiado durante al menos 5 segundos, por lo que hay un fallo en las
		// comunicaciones
		if (bitVidaCnt==10 && !bitVidaFallo)
		{
			bitVidaFallo = true;
		}
	}
	
	
	/******************************************************************************
	 * Funciones temporizadas
	 *****************************************************************************/
	/**
	 * Acciones para ejecutar cada 10 ms.
	 */
	private void task10ms()
	{
		
	}
	
	/**
	 * Acciones para ejecutar cada 50 ms.
	 */
	private void task50ms()
	{
		
	}
	
	/**
	 * Acciones para ejecutar cada 100 ms.
	 */
	private void task100ms()
	{		
		// Se activa la salida si el robot está listo para un movimiento
		if (robot.isReadyToMove())
		{
			Shared.oExtension.setReady(true);
		}
		else
		{
			Shared.oExtension.setReady(false);
		}
		
		// Se activa la salida si el robot tiene la llave en modo T1
		if (robot.getOperationMode().isModeT1())
		{
			Shared.oExtension.setT1(true);
			Shared.ioFlange.setLEDBlue(false);
		}
		else
		{
			Shared.oExtension.setT1(false);
		}
		
		// Se activa la salida si el robot tiene la llave en modo Auto
		if (robot.getOperationMode().isModeAuto())
		{
			Shared.oExtension.setAut(true);
			Shared.ioFlange.setLEDBlue(true);
		}
		else
		{
			Shared.oExtension.setAut(false);
		}
		
		// Acciones si la línea no está en automático
		if (!Shared.iExtension.getAutomaticoLinea())
		{
			// Si el robot está en automático hace caso a las señales de la pantalla
			if (robot.getOperationMode().isModeAuto())
			{
				// Abrir/Cerrar pinza
				if (Shared.iExtension.getPinzaCerrar())
				{
					Shared.ioFlange.setCerrarPinza(true);
				}
				if (Shared.iExtension.getPinzaAbrir())
				{
					Shared.ioFlange.setCerrarPinza(false);
				}
				
				// Activar/Desactivar vacío
				if (Shared.iExtension.getVacioOn())
				{
					Shared.ioFlange.setActivarVacio(true);
				}
				if (Shared.iExtension.getVacioOff())
				{
					Shared.ioFlange.setActivarVacio(false);
				}
			}
		}
		
		// Se activa la salida si el robot está en la posición Home y no estaba ya activa
		if (isInFrame(Shared.robot.getFlange(), Shared.HOME, 5.0) && !Shared.oExtension.getPosHome())
		{
			Shared.oExtension.setPosHome(true);
			Shared.oExtension.setPosFinNivel(false);
		}
		else if (!isInFrame(Shared.robot.getFlange(), Shared.HOME, 5.0))
		{
			Shared.oExtension.setPosHome(false);
		}
		
		// Se activa la salida si el robot está en la posición RobotInhibido y no estaba ya activa
		if (isInFrame(Shared.robot.getFlange(), Shared.ROBOTINHIBIDO, 50.0) && !Shared.oExtension.getPosInhibido())
		{
			Shared.oExtension.setPosInhibido(true);
		}
		else if (!isInFrame(Shared.robot.getFlange(), Shared.ROBOTINHIBIDO, 5.0))
		{
			Shared.oExtension.setPosInhibido(false);
		}
		
		// Se activa la salida si el robot está en la posición Vacio y no estaba ya activa
		if (isInFrame(Shared.garra.getFrame("/Vacio"), Shared.VACIO, 10.0) && !Shared.oExtension.getPosVacio())
		{
			Shared.oExtension.setPosVacio(true);
		}
		else if (!isInFrame(Shared.garra.getFrame("/Vacio"), Shared.VACIO, 10.0))
		{
			Shared.oExtension.setPosVacio(false);
		}
		
		// Se activa la salida si el robot está en la posición Nivel y no estaba ya activa
		if (isInFrame(Shared.garra.getFrame("/Nivel"), Shared.NIVEL, 10.0) && !Shared.oExtension.getPosNivel())
		{
			Shared.oExtension.setPosNivel(true);
		}
		else if (!isInFrame(Shared.garra.getFrame("/Nivel"), Shared.NIVEL, 10.0))
		{
			Shared.oExtension.setPosNivel(false);
		}
	}
	
	/**
	 * Acciones para ejecutar cada 200 ms.
	 */
	private void task200ms()
	{
		// Actualizar estado de los botones de abrir y cerrar pinza
		if (isPinzaAbierta() && !isPinzaCerrada())
		{
			btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
			btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		}
		else if (!isPinzaAbierta() && isPinzaCerrada())
		{
			btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
			btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
		}
		else
		{
			btnAbrirPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
			btnCerrarPinza.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
		}
		
		// Actualizar estado de los botones de activar y desactivar vacío
		if (isActVacio() && !isDesVacio())
		{
			btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
			btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
		}
		else if (!isActVacio() && isDesVacio())
		{
			btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Red, UserKeyLEDSize.Normal);
			btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Green, UserKeyLEDSize.Normal);
		}
		else
		{
			btnActVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
			btnDesVacio.setLED(UserKeyAlignment.TopMiddle, UserKeyLED.Yellow, UserKeyLEDSize.Normal);
		}
		
		// Deshabilitar botones en modo automático o si no se pulsa el botón de habilitación
		if (robot.getOperationMode().isModeAuto() ||
			robot.getSafetyState().getEnablingDeviceState().equals(EnablingDeviceState.NONE))
		{
			btnAbrirPinza.setEnabled(false);
			btnCerrarPinza.setEnabled(false);
			btnActVacio.setEnabled(false);
			btnDesVacio.setEnabled(false);
		}
		else
		{
			btnAbrirPinza.setEnabled(true);
			btnCerrarPinza.setEnabled(true);
			btnActVacio.setEnabled(true);
			btnDesVacio.setEnabled(true);
		}
	}
	
	/**
	 * Acciones para ejecutar cada 500 ms.
	 */
	private void task500ms()
	{
		// Se cambia el estado del Bit de Vida
		Shared.oExtension.setLifeBit(!Shared.oExtension.getLifeBit());
		
		// Se comprueba el Bit de Vida del PLC para confirmar que no hay fallo en las comunicaciones
		checkLifeBit();
		
		// Se comprueba si el robot ha sido movido a mano y necesita reposicionar
		reposicionar = getRecovery();
		Shared.oExtension.setRecovery(reposicionar.isRecoveryRequired());
		
		try
		{
			// Comprueba la posición actual para después comprobar si está o no en zona segura
			Shared.posAct = pos_act();
			
			/* Zona seguridad
			 * 
			 * Las coordenadas de la zona segura se comprueban según el mundo del robot
			 * SIN herramienta. Hay que tener en cuenta que la brida tiene una esfera segura
			 * con un radio de ~30mm
			 * 
			 * X: -1400		Y: -660		Z: 390
			 * X: 460		Y: 120		Z: 1400
			 */
			// Si la posición actual es mayor de lo especificado el robot no está en zona segura
			if ((Math.round(Shared.posAct.getX())>431))
			{
				Shared.oExtension.setZonaSeguraMotor(false);
			}
			else
			{
				Shared.oExtension.setZonaSeguraMotor(true);
			}
		}
		catch (Exception e)
		{
			
		}
	}
	
	/**
	 * Acciones para ejecutar cada 1000 ms.
	 */
	private void task1000ms()
	{
		
	}
	
	/**
	 * Acciones para ejecutar cada 2000 ms.
	 */
	private void task2000ms()
	{
		// Lee los valores del ProcessData
		readProcessData(getApplicationData());
		
		// Envía al PLC el estado de masterización de cada eje
		PositionMastering mastering = new PositionMastering(robot);
		int estadoEjes = 0;
		boolean isMastered;
    	for (int i=0; i<7; i++)
    	{
    		isMastered = mastering.isAxisMastered(i);
    		// Si el eje está masterizado modifica el bit correspondiete a ese eje y lo pone a 1, si no a 0
    		if (isMastered)
    		{
    			estadoEjes = estadoEjes | (1 << i);		// Modificar el bit i a 1
    		}
    		else
    		{
    			estadoEjes = estadoEjes & ~(1 << i);	// Modificar el bit i a 0
    		}
    	}
    	Shared.oExtension.setCodError(estadoEjes);
	}
}