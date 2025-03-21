package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;
import com.kuka.roboticsAPI.ioModel.OutputReservedException;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>OutputsProfinet</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * Salidas de Profinet para comunicar con el PLC
 */
@Singleton
public class OutputsProfinetIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'OutputsProfinet'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'OutputsProfinet'
	 */
	@Inject
	public OutputsProfinetIOGroup(Controller controller)
	{
		super(controller, "OutputsProfinet");

		addDigitalOutput("Ready", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ErrorPrograma", IOTypes.BOOLEAN, 1);
		addDigitalOutput("CodigoError", IOTypes.UNSIGNED_INTEGER, 5);
		addDigitalOutput("T1", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Aut", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AutExtActive", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("AutExtAppReadyToStart", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("DefaultAppError", IOTypes.BOOLEAN, 1);
		addMockedDigitalOutput("StationError", IOTypes.BOOLEAN, 1);
		addDigitalOutput("LifeBit", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ZonaSeguraMotor", IOTypes.BOOLEAN, 1);
		addDigitalOutput("PosHome", IOTypes.BOOLEAN, 1);
		addDigitalOutput("PosVacio", IOTypes.BOOLEAN, 1);
		addDigitalOutput("PosNivel", IOTypes.BOOLEAN, 1);
		addDigitalOutput("PosFinNivel", IOTypes.BOOLEAN, 1);
		addDigitalOutput("PosInhibido", IOTypes.BOOLEAN, 1);
		addDigitalOutput("BusqCuadNoOK", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ReposNoOK", IOTypes.BOOLEAN, 1);
		addDigitalOutput("Recovery", IOTypes.BOOLEAN, 1);
		addDigitalOutput("CodError", IOTypes.INTEGER, 16);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Ready</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si el robot esta listo para moverse
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Ready'
	 */
	public boolean getReady()
	{
		return getBooleanIOValue("Ready", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Ready</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si el robot esta listo para moverse
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Ready'
	 */
	public void setReady(java.lang.Boolean value)
	{
		setDigitalOutput("Ready", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ErrorPrograma</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si hay un fallo en el programa
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'ErrorPrograma'
	 */
	public boolean getErrorPrograma()
	{
		return getBooleanIOValue("ErrorPrograma", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ErrorPrograma</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si hay un fallo en el programa
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'ErrorPrograma'
	 */
	public void setErrorPrograma(java.lang.Boolean value)
	{
		setDigitalOutput("ErrorPrograma", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>CodigoError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Para enviar distintos codigos de error segun el que se haya producido
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 31]
	 *
	 * @return current value of the digital output 'CodigoError'
	 */
	public java.lang.Integer getCodigoError()
	{
		return getNumberIOValue("CodigoError", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>CodigoError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Para enviar distintos codigos de error segun el que se haya producido
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [0; 31]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'CodigoError'
	 */
	public void setCodigoError(java.lang.Integer value)
	{
		setDigitalOutput("CodigoError", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>T1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en modo manual
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'T1'
	 */
	public boolean getT1()
	{
		return getBooleanIOValue("T1", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>T1</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en modo manual
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'T1'
	 */
	public void setT1(java.lang.Boolean value)
	{
		setDigitalOutput("T1", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Aut</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en modo automatico
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Aut'
	 */
	public boolean getAut()
	{
		return getBooleanIOValue("Aut", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Aut</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot esta en modo automatico
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Aut'
	 */
	public void setAut(java.lang.Boolean value)
	{
		setDigitalOutput("Aut", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>AutExtActive</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica que el robot esta listo para ser controlado por un PLC externo
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AutExtActive'
	* 
	 * @deprecated The output 'AutExtActive' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getAutExtActive()
	{
		return getBooleanIOValue("AutExtActive", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>AutExtActive</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica que el robot esta listo para ser controlado por un PLC externo
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AutExtActive'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'AutExtActive' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setAutExtActive(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'AutExtActive' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>AutExtAppReadyToStart</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica que la aplicacion esta lista para ser iniciada externamente
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'AutExtAppReadyToStart'
	* 
	 * @deprecated The output 'AutExtAppReadyToStart' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getAutExtAppReadyToStart()
	{
		return getBooleanIOValue("AutExtAppReadyToStart", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>AutExtAppReadyToStart</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica que la aplicacion esta lista para ser iniciada externamente
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'AutExtAppReadyToStart'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'AutExtAppReadyToStart' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setAutExtAppReadyToStart(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'AutExtAppReadyToStart' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>DefaultAppError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si hay algun error en la aplicacion principal
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'DefaultAppError'
	* 
	 * @deprecated The output 'DefaultAppError' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getDefaultAppError()
	{
		return getBooleanIOValue("DefaultAppError", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>DefaultAppError</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si hay algun error en la aplicacion principal
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'DefaultAppError'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'DefaultAppError' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setDefaultAppError(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'DefaultAppError' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>StationError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si hay algun error con la controladora o el robot
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'StationError'
	* 
	 * @deprecated The output 'StationError' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public boolean getStationError()
	{
		return getBooleanIOValue("StationError", true);
	}

	/**
	 * Always throws an {@code OutputReservedException}, because the <b>digital output '<i>StationError</i>'</b> is currently used as station state output in the Sunrise project properties.
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si hay algun error con la controladora o el robot
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'StationError'
	 * @throws OutputReservedException
	 *            Always thrown, because this output is currently used as station state output in the Sunrise project properties.
	* 
	 * @deprecated The output 'StationError' is currently used as station state output in the Sunrise project properties.
	 */
	@Deprecated
	public void setStationError(java.lang.Boolean value) throws OutputReservedException
	{
		throw new OutputReservedException("The output 'StationError' must not be set because it is currently used as station state output in the Sunrise project properties.");
	}

	/**
	 * Gets the value of the <b>digital output '<i>LifeBit</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Bit de Vida del robot para comprobar que no se pierde la comunicacion
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'LifeBit'
	 */
	public boolean getLifeBit()
	{
		return getBooleanIOValue("LifeBit", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>LifeBit</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Bit de Vida del robot para comprobar que no se pierde la comunicacion
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'LifeBit'
	 */
	public void setLifeBit(java.lang.Boolean value)
	{
		setDigitalOutput("LifeBit", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ZonaSeguraMotor</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si el robot esta en una zona segura donde no puede ser golpeado por un motor
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'ZonaSeguraMotor'
	 */
	public boolean getZonaSeguraMotor()
	{
		return getBooleanIOValue("ZonaSeguraMotor", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ZonaSeguraMotor</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Indica si el robot esta en una zona segura donde no puede ser golpeado por un motor
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'ZonaSeguraMotor'
	 */
	public void setZonaSeguraMotor(java.lang.Boolean value)
	{
		setDigitalOutput("ZonaSeguraMotor", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>PosHome</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion Home
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'PosHome'
	 */
	public boolean getPosHome()
	{
		return getBooleanIOValue("PosHome", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>PosHome</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion Home
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'PosHome'
	 */
	public void setPosHome(java.lang.Boolean value)
	{
		setDigitalOutput("PosHome", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>PosVacio</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion para medir vacio
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'PosVacio'
	 */
	public boolean getPosVacio()
	{
		return getBooleanIOValue("PosVacio", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>PosVacio</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion para medir vacio
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'PosVacio'
	 */
	public void setPosVacio(java.lang.Boolean value)
	{
		setDigitalOutput("PosVacio", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>PosNivel</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion para medir nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'PosNivel'
	 */
	public boolean getPosNivel()
	{
		return getBooleanIOValue("PosNivel", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>PosNivel</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion para medir nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'PosNivel'
	 */
	public void setPosNivel(java.lang.Boolean value)
	{
		setDigitalOutput("PosNivel", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>PosFinNivel</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot ha salido de la posicion de medir nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'PosFinNivel'
	 */
	public boolean getPosFinNivel()
	{
		return getBooleanIOValue("PosFinNivel", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>PosFinNivel</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot ha salido de la posicion de medir nivel
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'PosFinNivel'
	 */
	public void setPosFinNivel(java.lang.Boolean value)
	{
		setDigitalOutput("PosFinNivel", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>PosInhibido</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion de inhibido
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'PosInhibido'
	 */
	public boolean getPosInhibido()
	{
		return getBooleanIOValue("PosInhibido", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>PosInhibido</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Marca que indica que el robot esta en la posicion de inhibido
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'PosInhibido'
	 */
	public void setPosInhibido(java.lang.Boolean value)
	{
		setDigitalOutput("PosInhibido", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>BusqCuadNoOK</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si no se ha podido encontrar la sonda de nivel con el conector cuadrado
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'BusqCuadNoOK'
	 */
	public boolean getBusqCuadNoOK()
	{
		return getBooleanIOValue("BusqCuadNoOK", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>BusqCuadNoOK</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si no se ha podido encontrar la sonda de nivel con el conector cuadrado
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'BusqCuadNoOK'
	 */
	public void setBusqCuadNoOK(java.lang.Boolean value)
	{
		setDigitalOutput("BusqCuadNoOK", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ReposNoOK</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot no esta en una posicion conocida para reposicionar hasta Home
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'ReposNoOK'
	 */
	public boolean getReposNoOK()
	{
		return getBooleanIOValue("ReposNoOK", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ReposNoOK</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot no esta en una posicion conocida para reposicionar hasta Home
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'ReposNoOK'
	 */
	public void setReposNoOK(java.lang.Boolean value)
	{
		setDigitalOutput("ReposNoOK", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>Recovery</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot ha sido movido manualmente y necesita reposicionar
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'Recovery'
	 */
	public boolean getRecovery()
	{
		return getBooleanIOValue("Recovery", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>Recovery</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Se activa si el robot ha sido movido manualmente y necesita reposicionar
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'Recovery'
	 */
	public void setRecovery(java.lang.Boolean value)
	{
		setDigitalOutput("Recovery", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>CodError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Codigo para indicar al PLC los errores que el robot tenga activos
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @return current value of the digital output 'CodError'
	 */
	public java.lang.Integer getCodError()
	{
		return getNumberIOValue("CodError", true).intValue();
	}

	/**
	 * Sets the value of the <b>digital output '<i>CodError</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * Codigo para indicar al PLC los errores que el robot tenga activos
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [-32768; 32767]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'CodError'
	 */
	public void setCodError(java.lang.Integer value)
	{
		setDigitalOutput("CodError", value);
	}

}
