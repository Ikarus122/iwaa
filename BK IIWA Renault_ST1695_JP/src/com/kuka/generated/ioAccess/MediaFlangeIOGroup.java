package com.kuka.generated.ioAccess;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.kuka.roboticsAPI.controllerModel.Controller;
import com.kuka.roboticsAPI.ioModel.AbstractIOGroup;
import com.kuka.roboticsAPI.ioModel.IOTypes;

/**
 * Automatically generated class to abstract I/O access to I/O group <b>MediaFlange</b>.<br>
 * <i>Please, do not modify!</i>
 * <p>
 * <b>I/O group description:</b><br>
 * This I/O Group contains the In-/Outputs for the Media-Flange Touch.
 */
@Singleton
public class MediaFlangeIOGroup extends AbstractIOGroup
{
	/**
	 * Constructor to create an instance of class 'MediaFlange'.<br>
	 * <i>This constructor is automatically generated. Please, do not modify!</i>
	 *
	 * @param controller
	 *            the controller, which has access to the I/O group 'MediaFlange'
	 */
	@Inject
	public MediaFlangeIOGroup(Controller controller)
	{
		super(controller, "MediaFlange");

		addInput("InputX3Pin3", IOTypes.BOOLEAN, 1);
		addInput("InputX3Pin4", IOTypes.BOOLEAN, 1);
		addInput("UserButton", IOTypes.BOOLEAN, 1);
		addDigitalOutput("LEDBlue", IOTypes.BOOLEAN, 1);
		addDigitalOutput("SwitchOffX3Voltage", IOTypes.BOOLEAN, 1);
		addDigitalOutput("OutputX3Pin2", IOTypes.BOOLEAN, 1);
		addDigitalOutput("OutputX3Pin12", IOTypes.BOOLEAN, 1);
		addDigitalOutput("ActivarVacio", IOTypes.BOOLEAN, 1);
		addDigitalOutput("CerrarPinza", IOTypes.BOOLEAN, 1);
		addInput("PinzaAbierta", IOTypes.BOOLEAN, 1);
		addInput("VacioDesactivado", IOTypes.BOOLEAN, 1);
		addInput("VacioOK", IOTypes.BOOLEAN, 1);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InputX3Pin3</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InputX3Pin3'
	 */
	public boolean getInputX3Pin3()
	{
		return getBooleanIOValue("InputX3Pin3", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>InputX3Pin4</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'InputX3Pin4'
	 */
	public boolean getInputX3Pin4()
	{
		return getBooleanIOValue("InputX3Pin4", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>UserButton</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'UserButton'
	 */
	public boolean getUserButton()
	{
		return getBooleanIOValue("UserButton", false);
	}

	/**
	 * Gets the value of the <b>digital output '<i>LEDBlue</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'LEDBlue'
	 */
	public boolean getLEDBlue()
	{
		return getBooleanIOValue("LEDBlue", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>LEDBlue</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'LEDBlue'
	 */
	public void setLEDBlue(java.lang.Boolean value)
	{
		setDigitalOutput("LEDBlue", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>SwitchOffX3Voltage</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'SwitchOffX3Voltage'
	 */
	public boolean getSwitchOffX3Voltage()
	{
		return getBooleanIOValue("SwitchOffX3Voltage", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>SwitchOffX3Voltage</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'SwitchOffX3Voltage'
	 */
	public void setSwitchOffX3Voltage(java.lang.Boolean value)
	{
		setDigitalOutput("SwitchOffX3Voltage", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>OutputX3Pin2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'OutputX3Pin2'
	 */
	public boolean getOutputX3Pin2()
	{
		return getBooleanIOValue("OutputX3Pin2", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>OutputX3Pin2</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'OutputX3Pin2'
	 */
	public void setOutputX3Pin2(java.lang.Boolean value)
	{
		setDigitalOutput("OutputX3Pin2", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>OutputX3Pin12</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'OutputX3Pin12'
	 */
	public boolean getOutputX3Pin12()
	{
		return getBooleanIOValue("OutputX3Pin12", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>OutputX3Pin12</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'OutputX3Pin12'
	 */
	public void setOutputX3Pin12(java.lang.Boolean value)
	{
		setDigitalOutput("OutputX3Pin12", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>ActivarVacio</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'ActivarVacio'
	 */
	public boolean getActivarVacio()
	{
		return getBooleanIOValue("ActivarVacio", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>ActivarVacio</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'ActivarVacio'
	 */
	public void setActivarVacio(java.lang.Boolean value)
	{
		setDigitalOutput("ActivarVacio", value);
	}

	/**
	 * Gets the value of the <b>digital output '<i>CerrarPinza</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital output 'CerrarPinza'
	 */
	public boolean getCerrarPinza()
	{
		return getBooleanIOValue("CerrarPinza", true);
	}

	/**
	 * Sets the value of the <b>digital output '<i>CerrarPinza</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital output
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @param value
	 *            the value, which has to be written to the digital output 'CerrarPinza'
	 */
	public void setCerrarPinza(java.lang.Boolean value)
	{
		setDigitalOutput("CerrarPinza", value);
	}

	/**
	 * Gets the value of the <b>digital input '<i>PinzaAbierta</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'PinzaAbierta'
	 */
	public boolean getPinzaAbierta()
	{
		return getBooleanIOValue("PinzaAbierta", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>VacioDesactivado</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'VacioDesactivado'
	 */
	public boolean getVacioDesactivado()
	{
		return getBooleanIOValue("VacioDesactivado", false);
	}

	/**
	 * Gets the value of the <b>digital input '<i>VacioOK</i>'</b>.<br>
	 * <i>This method is automatically generated. Please, do not modify!</i>
	 * <p>
	 * <b>I/O direction and type:</b><br>
	 * digital input
	 * <p>
	 * <b>User description of the I/O:</b><br>
	 * ./.
	 * <p>
	 * <b>Range of the I/O value:</b><br>
	 * [false; true]
	 *
	 * @return current value of the digital input 'VacioOK'
	 */
	public boolean getVacioOK()
	{
		return getBooleanIOValue("VacioOK", false);
	}

}
