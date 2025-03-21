package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>InputsProfinet</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Entradas de Profinet para comunicar con el PLC
 */
@Singleton
public class InputsProfinetIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'InputsProfinet'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'InputsProfinet'
	 */
	@Inject
	public InputsProfinetIOGroup(Controller controller)
	{
		super(controller, "InputsProfinet");

		addInput("ReadyLinea", IOTypes.BOOLEAN, 1);
		addInput("AutomaticoLinea", IOTypes.BOOLEAN, 1);
		addInput("LifeBit", IOTypes.BOOLEAN, 1);
		addInput("CodMot", IOTypes.UNSIGNED_INTEGER, 4);
		addInput("AppStart", IOTypes.BOOLEAN, 1);
		addInput("AppEnable", IOTypes.BOOLEAN, 1);
		addInput("RbtPos1", IOTypes.BOOLEAN, 1);
		addInput("RbtPos2", IOTypes.BOOLEAN, 1);
		addInput("VacioOn", IOTypes.BOOLEAN, 1);
		addInput("PinzaCerrar", IOTypes.BOOLEAN, 1);
		addInput("ErrorSonda", IOTypes.BOOLEAN, 1);
		addInput("CodCon", IOTypes.UNSIGNED_INTEGER, 2);
		addInput("Home", IOTypes.BOOLEAN, 1);
		addInput("QuitarTapon", IOTypes.BOOLEAN, 1);
		addInput("MedirVacio", IOTypes.BOOLEAN, 1);
		addInput("MedirNivel", IOTypes.BOOLEAN, 1);
		addInput("RobotInhibido", IOTypes.BOOLEAN, 1);
		addInput("VacioOff", IOTypes.BOOLEAN, 1);
		addInput("PinzaAbrir", IOTypes.BOOLEAN, 1);
		addInput("Grados", IOTypes.INTEGER, 16);
	}

	/**
	 * Gets the value of the <b>digital input '<i>ReadyLinea</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si la linea cumple las condiciones para trabajar
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'ReadyLinea'
	 */
	public boolean getReadyLinea()
	{
		return getBooleanIOValue("ReadyLinea", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AutomaticoLinea</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si el selector de la línea está en automático
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AutomaticoLinea'
	 */
	public boolean getAutomaticoLinea()
	{
		return getBooleanIOValue("AutomaticoLinea", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>LifeBit</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Bit de Vida del PLC para comprobar que no se pierde la comunicacion
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'LifeBit'
	 */
	public boolean getLifeBit()
	{
		return getBooleanIOValue("LifeBit", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>CodMot</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Codigo del tipo de motor leido por el PLC
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 15]
	 *
	 * @return current value of the digital input 'CodMot'
	 */
	public java.lang.Integer getCodMot()
	{
		return getNumberIOValue("CodMot", false).intValue();
	}

	/**
	 * Gets the value of the <b>digital input '<i>AppStart</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Inicia la aplicacion
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AppStart'
	 */
	public boolean getAppStart()
	{
		return getBooleanIOValue("AppStart", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>AppEnable</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Habilita la ejecucion de la aplicacion
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'AppEnable'
	 */
	public boolean getAppEnable()
	{
		return getBooleanIOValue("AppEnable", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>RbtPos1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en la posicion 1 (trabajo)
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'RbtPos1'
	 */
	public boolean getRbtPos1()
	{
		return getBooleanIOValue("RbtPos1", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>RbtPos2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en la posicion 2 (inhabilitado)
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'RbtPos2'
	 */
	public boolean getRbtPos2()
	{
		return getBooleanIOValue("RbtPos2", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>VacioOn</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Activa el vacio de la garra desde la pantalla
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'VacioOn'
	 */
	public boolean getVacioOn()
	{
		return getBooleanIOValue("VacioOn", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>PinzaCerrar</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Cierra la pinza desde la pantalla
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'PinzaCerrar'
	 */
	public boolean getPinzaCerrar()
	{
		return getBooleanIOValue("PinzaCerrar", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>ErrorSonda</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si la sonda de nivel esta rota o da fallo
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'ErrorSonda'
	 */
	public boolean getErrorSonda()
	{
		return getBooleanIOValue("ErrorSonda", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>CodCon</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Codigo del tipo de conector especificado en pantalla
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 3]
	 *
	 * @return current value of the digital input 'CodCon'
	 */
	public java.lang.Integer getCodCon()
	{
		return getNumberIOValue("CodCon", false).intValue();
	}

	/**
	 * Gets the value of the <b>digital input '<i>Home</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca para indicar al robot que se mueva a Home
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'Home'
	 */
	public boolean getHome()
	{
		return getBooleanIOValue("Home", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>QuitarTapon</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca para indicar al robot que inicie la secuencia de quitar tapon
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'QuitarTapon'
	 */
	public boolean getQuitarTapon()
	{
		return getBooleanIOValue("QuitarTapon", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>MedirVacio</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca para indicar al robot que inicie la secuencia de medir vacio
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'MedirVacio'
	 */
	public boolean getMedirVacio()
	{
		return getBooleanIOValue("MedirVacio", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>MedirNivel</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca para indicar al robot que inicie la secuencia de medir nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'MedirNivel'
	 */
	public boolean getMedirNivel()
	{
		return getBooleanIOValue("MedirNivel", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>RobotInhibido</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca para indicar al robot que se mueva a la posicion de inhibido
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'RobotInhibido'
	 */
	public boolean getRobotInhibido()
	{
		return getBooleanIOValue("RobotInhibido", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>VacioOff</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Desactiva el vacío de la garra desde la pantalla
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'VacioOff'
	 */
	public boolean getVacioOff()
	{
		return getBooleanIOValue("VacioOff", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>PinzaAbrir</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Abre la pinza desde la pantalla
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'PinzaAbrir'
	 */
	public boolean getPinzaAbrir()
	{
		return getBooleanIOValue("PinzaAbrir", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>Grados</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Numero de grados para girar en la busqueda de la sonda de nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @return current value of the digital input 'Grados'
	 */
	public java.lang.Integer getGrados()
	{
		return getNumberIOValue("Grados", false).intValue();
	}

}
