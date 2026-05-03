/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


package environments.exp3;

import technicals.datacenterLarge;
import technicals.datacenterMedium;
import technicals.datacenterSmall;
import technicals.simulationParameters;

import java.util.Calendar;
import java.util.List;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterBroker;
import org.cloudbus.cloudsim.Log;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.core.CloudSim;

import brokers.GeneticAlgorithm.GeneticAlgorithmDatacenterBroker;

/**
 * An example showing how to create
 * scalable simulations.
 */
public class GASimulation {
	public static DatacenterBroker broker;

	/** The cloudlet list. */
	private static List<Cloudlet> cloudletList;

	/** The vmlist. */
	private static List<Vm> vmlist;

	public static void main(String[] args) {
		Log.println("Starting baselineSingularDatacenter...");

		try {
			simulationParameters sp = new simulationParameters();
			// First step: Initialize the CloudSim package. It should be called
			// before creating any entities.
			int num_user = 1;   // number of grid users
			Calendar calendar = Calendar.getInstance();
			boolean trace_flag = false;  // mean trace events

			// Initialize the CloudSim library
			CloudSim.init(num_user, calendar, trace_flag);

			// Second step: Create Datacenters
			//Datacenters are the resource providers in CloudSim. We need at least one of them to run a CloudSim simulation
			Datacenter datacenter0 = datacenterSmall.createDatacenter("Datacenter_0");
			Datacenter datacenter1 = datacenterSmall.createDatacenter("Datacenter_1");
			Datacenter datacenter2 = datacenterSmall.createDatacenter("Datacenter_2");
			Datacenter datacenter3 = datacenterSmall.createDatacenter("Datacenter_3");
			Datacenter datacenter4 = datacenterSmall.createDatacenter("Datacenter_4");
			Datacenter datacenter5 = datacenterSmall.createDatacenter("Datacenter_5");
			Datacenter datacenter6 = datacenterMedium.createDatacenter("Datacenter_6");
			Datacenter datacenter7 = datacenterMedium.createDatacenter("Datacenter_7");
			Datacenter datacenter8 = datacenterMedium.createDatacenter("Datacenter_8");
			Datacenter datacenter9 = datacenterMedium.createDatacenter("Datacenter_9");
			Datacenter datacenter10 = datacenterLarge.createDatacenter("Datacenter_10");
			Datacenter datacenter11 = datacenterLarge.createDatacenter("Datacenter_11");

			//Third step: Create Broker
			broker = new GeneticAlgorithmDatacenterBroker("Broker");;
			int brokerId = broker.getId();

			vmlist = simulationParameters.createVM(brokerId,sp.numVmsExp4); 
			cloudletList = simulationParameters.createCloudletHeterogenous(brokerId,sp.cloudletNumExp4); 

			broker.submitGuestList(vmlist);
			broker.submitCloudletList(cloudletList);

			// Fifth step: Starts the simulation
			CloudSim.startSimulation();

			// Final step: Print results when simulation is over
			List<Cloudlet> newList = broker.getCloudletReceivedList();

			CloudSim.stopSimulation();

			//printCloudletList(newList);
			String path = "modules/cloudsim-simulations/src/main/java/results/";
			simulationParameters.writeCloudletListToCSV(newList, path + "GAexp3.csv");
			System.out.println(brokerId);

			Log.println("CloudSimExample6 finished!");
		}
		catch (Exception e)
		{
			e.printStackTrace();
			Log.println("The simulation has been terminated due to an unexpected error");
		}
	}
	
}
