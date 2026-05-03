/*
 * Title:        CloudSim Toolkit
 * Description:  CloudSim (Cloud Simulation) Toolkit for Modeling and Simulation
 *               of Clouds
 * Licence:      GPL - http://www.gnu.org/copyleft/gpl.html
 *
 * Copyright (c) 2009, The University of Melbourne, Australia
 */


package environments.exp2;

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


import brokers.CustomMLBased.ABCDatacenterBroker;

/**
 * An example showing how to create
 * scalable simulations.
 */
public class ABCSimulation {
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
			Datacenter datacenter2 = datacenterMedium.createDatacenter("Datacenter_2");
			Datacenter datacenter3 = datacenterLarge.createDatacenter("Datacenter_3");

			//Third step: Create Broker
			broker = new ABCDatacenterBroker("Broker");;
			int brokerId = broker.getId();

			vmlist = simulationParameters.createVMsGradually(brokerId, 12); 
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
			simulationParameters.writeCloudletListToCSV(newList, path + "ABCexp2.csv");
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
