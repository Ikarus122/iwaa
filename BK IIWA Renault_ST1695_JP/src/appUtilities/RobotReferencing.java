package appUtilities;

import static com.kuka.roboticsAPI.motionModel.BasicMotions.*;

import appShared.Shared;

import com.kuka.common.ThreadUtil;
import com.kuka.roboticsAPI.controllerModel.sunrise.ISunriseRequestService;
import com.kuka.roboticsAPI.controllerModel.sunrise.api.SSR;
import com.kuka.roboticsAPI.controllerModel.sunrise.api.SSRFactory;
import com.kuka.roboticsAPI.controllerModel.sunrise.connectionLib.Message;
import com.kuka.roboticsAPI.controllerModel.sunrise.positionMastering.PositionMastering;
import com.kuka.roboticsAPI.deviceModel.JointPosition;
import com.kuka.roboticsAPI.deviceModel.LBR;
import com.kuka.roboticsAPI.motionModel.PTP;

/**
 * Clase para realizar la referencia GMS y de Posición del robot.
 * El tiempo entre dos medidas debe ser menor de 15 segundos.
 */
public class RobotReferencing
{
	/******************************************************************************
	 * Variables
	 *****************************************************************************/
	private LBR robot = null;
	private final static double desvio = Math.toRadians(5);				// Desvío en radianes para los movimientos laterales
    private final static int ejesID[] = {0, 1, 2, 3, 4, 5, 6};			// Número de los ejes para ser referenciados
    private static double velocidadMovimiento = 0.2;					// Velocidad de movimiento
    private int contadorPosicion;

    
    /******************************************************************************
	 * Funciones
	 *****************************************************************************/
    /**
     * Constructor de la clase RobotReferencing.
     */
    public RobotReferencing()
    {
		robot = Shared.robot;
    }
    
    /**
     * Comprueba si todos los ejes están referenciados.
     * @return True si están referenciados, False en caso contrario
     */
    public boolean getReferenciar()
    {
    	return robot.getSafetyState().areAllAxesPositionReferenced();
    }
    
    /**
     * Comprueba si todos los ejes están masterizados.
     * @return True si están masterizados, False en caso contrario
     */
    public boolean getMasterizar()
    {
    	PositionMastering mastering = new PositionMastering(robot);
    	boolean ejesMasterizados = true;
    	
    	for (int i=0; i<ejesID.length; i++)
    	{
    		// Comprueba si el eje está masterizado. Si no lo está, es imposible referenciar ni moverse
    		boolean isMastered = mastering.isAxisMastered(ejesID[i]);
    		ejesMasterizados &= isMastered;
    	}
    	
    	return ejesMasterizados;
    }

    /**
     * Ejecuta la secuencia para referenciar los ejes del robot.
     */
    public void setReferenciar()
    {
			contadorPosicion = 0;
			boolean ejesMasterizados = getMasterizar();
			
			// Si está en Auto, se mueve más lento que en manual
			if (robot.getOperationMode().isModeAuto())
			{
			    velocidadMovimiento = 0.1;
			}
			else
			{
			    velocidadMovimiento = 0.8;
			}
			
			// Si todos los ejes están masterizados, empieza a referenciarlos
			if (ejesMasterizados)
			{
			    Shared.appLogger.info("Realizando referenciado de los ejes");
			
				// Mover a posición inicial
				robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(velocidadMovimiento));
				robot.move(ptp(Shared.REFERENCIARFRAME).setJointVelocityRel(velocidadMovimiento));
				
				// Se define las posiciones de los ejes para referenciarlos
				realizarMovimiento(new JointPosition(Math.toRadians(39.0),
								                    Math.toRadians(-21.6),
								                    Math.toRadians(50.0),
								                    Math.toRadians(14.5),
								                    Math.toRadians(33.0),
								                    Math.toRadians(21.3),
								                    Math.toRadians(25.2)));
				
				realizarMovimiento(new JointPosition(Math.toRadians(-32.9),
								                    Math.toRadians(51.2),
								                    Math.toRadians(-102.4),
								                    Math.toRadians(-77.7),
								                    Math.toRadians(61.1),
								                    Math.toRadians(-51.3),
								                    Math.toRadians(-44.8)));
				
				realizarMovimiento(new JointPosition(Math.toRadians(-51.0),
								                    Math.toRadians(16.0),
								                    Math.toRadians(-42.0),
								                    Math.toRadians(-17.0),
								                    Math.toRadians(-48.2),
								                    Math.toRadians(-25.0),
								                    Math.toRadians(-27.0)));
				
				realizarMovimiento(new JointPosition(Math.toRadians(12.4),
								                    Math.toRadians(42.1),
								                    Math.toRadians(-4.0),
								                    Math.toRadians(94.0),
								                    Math.toRadians(56.0),
								                    Math.toRadians(-11.0),
								                    Math.toRadians(-54.0)));
				
				realizarMovimiento(new JointPosition(Math.toRadians(-118.0),
								                    Math.toRadians(33.0),
								                    Math.toRadians(-55.0),
								                    Math.toRadians(-69.0),
								                    Math.toRadians(74.0),
								                    Math.toRadians(-68.0),
								                    Math.toRadians(-47.0)));
				
				// Mover a posición Home
				ThreadUtil.milliSleep(1000);
				// Se pone a falso para evitar que referencie entre ciclos
				// Volverá a referenciar si se vuelve a activar desde el ProcessData
				Shared.appData.getProcessData("activarReferenciado").setValue(false);
				robot.move(ptp(Shared.appData.getFrame(Shared.REFERENCIAR)).setJointVelocityRel(velocidadMovimiento));
				robot.move(ptp(Shared.appData.getFrame(Shared.HOME + "/InterHome")).setJointVelocityRel(velocidadMovimiento));
				robot.move(ptp(Shared.HOMEFRAME).setJointVelocityRel(velocidadMovimiento));
			}
    }

    /**
     * Realiza los movimientos (positivos y negativos) para referenciar los ejes.
     * @param posicion Posición a la que debe moverse
     */
    private void realizarMovimiento(JointPosition posicion)
    {
		Shared.appLogger.info("Moviendo a posición #" + (++contadorPosicion));
		PTP movPrincipal = new PTP(posicion).setJointVelocityRel(velocidadMovimiento);
		robot.move(movPrincipal);
		
		
		// Referenciar ejes 1-7
		Shared.appLogger.info("Moviendo a la posición desde la dirección negativa");
		JointPosition pos1 = new JointPosition(robot.getJointCount());
		for (int i=0; i<robot.getJointCount(); i++)
		{
			pos1.set(i, posicion.get(i) - desvio);
		}
		PTP motion1 = new PTP(pos1).setJointVelocityRel(velocidadMovimiento);
		robot.move(motion1);
		robot.move(movPrincipal);
		// Espera un tiempo para reducir la vibración del robot tras la parada
		ThreadUtil.milliSleep(1500);
		// Envía el comando a seguridad para activar la medida
		enviarComandoSeguridad();
		
		Shared.appLogger.info("Moviendo a la posición desde la dirección positiva");
		JointPosition pos2 = new JointPosition(robot.getJointCount());
		for (int i=0; i<robot.getJointCount(); i++)
		{
			pos2.set(i, posicion.get(i) + desvio);
		}
		PTP motion2 = new PTP(pos2).setJointVelocityRel(velocidadMovimiento);
		robot.move(motion2);
		robot.move(movPrincipal);
		ThreadUtil.milliSleep(1500);
		enviarComandoSeguridad();
    }
    
    /**
     * Envía el comando al robot para indicar que el eje está referenciado.
     */
    private void enviarComandoSeguridad()
    {
    	int comandoReferenciaGMS = 2;			// Comando de seguridad para la referencia GMS
    	int comandoCorrecto = 1;
    	
        ISunriseRequestService requestService = (ISunriseRequestService) (Shared.controladora.getRequestService());
        SSR ssr = SSRFactory.createSafetyCommandSSR(comandoReferenciaGMS);
        Message respuesta = requestService.sendSynchronousSSR(ssr);
        
        int resultado = respuesta.getParamInt(0);
        if (comandoCorrecto != resultado)
        {
            Shared.appLogger.warn("El comando no se ejecutó correctamente, respuesta: " + resultado);
        }
    }
}
