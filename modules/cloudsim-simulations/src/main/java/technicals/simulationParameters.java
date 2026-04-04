package technicals;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.cloudbus.cloudsim.Cloudlet;
import org.cloudbus.cloudsim.CloudletSchedulerSpaceShared;
import org.cloudbus.cloudsim.Datacenter;
import org.cloudbus.cloudsim.DatacenterCharacteristics;
import org.cloudbus.cloudsim.Host;
import org.cloudbus.cloudsim.Pe;
import org.cloudbus.cloudsim.Storage;
import org.cloudbus.cloudsim.UtilizationModel;
import org.cloudbus.cloudsim.UtilizationModelStochastic;
import org.cloudbus.cloudsim.Vm;
import org.cloudbus.cloudsim.VmAllocationPolicySimple;
import org.cloudbus.cloudsim.VmSchedulerTimeShared;
import org.cloudbus.cloudsim.provisioners.BwProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.PeProvisionerSimple;
import org.cloudbus.cloudsim.provisioners.RamProvisionerSimple;

public class simulationParameters {
	public int cloudletNumber = 5000;
	public int num_vms_singleDC = 3;

	public static List<Vm> createVM(int userId, final int vms) {
		List<Vm> list = new ArrayList<>();

		//VM Parameters
		long size = 10000; //image size (MB)
		int ram = 4000; //vm memory (MB)
		int mips = 512;
		long bw = 500;
		int pesNumber = 1; //number of cpus
		String vmm = "Xen"; //VMM name

		for(int i=0;i<vms;i++){
			list.add(new Vm(i, userId, mips, pesNumber, ram, bw, size, vmm, new CloudletSchedulerSpaceShared()));
		}

		return list;
	}

	public static List<Cloudlet> createCloudletHeterogenous(int userId, int cloudlets){
		List<Cloudlet> list = new ArrayList<>();
		Random rand = new Random();

		for(int i=0;i<cloudlets;i++){
			long length = 20000 + rand.nextInt(30000);;
			long fileSize = 700 + rand.nextInt(800); 
			long outputSize = 700 + rand.nextInt(300);
			int pesNumber = 1;
			UtilizationModel utilizationModel = new UtilizationModelStochastic();
			list.add(new Cloudlet(i, length, pesNumber, fileSize, outputSize, utilizationModel, utilizationModel, utilizationModel));
			list.getLast().setUserId(userId);
		}

		return list;
	}
    
    public static void writeCloudletListToCSV(List<Cloudlet> list, String filePath) {
		StringBuilder sb = new StringBuilder();
		DecimalFormat dft = new DecimalFormat("###.##");
	
		// Header
		sb.append("Cloudlet ID,")
		.append("VM ID,")
		  .append("User ID,")
		  .append("Status,")
		  .append("Data Center ID,")
		  .append("Submission Time,")
		  .append("Start Time,")
		  .append("Finish Time,")
		  .append("Cloudlet Length,Processing Cost,File Size,")
		  .append("CPU Utilization,RAM Utilization,BW Utilization,Waiting Time\n");
		  
	
		for (Cloudlet cloudlet : list) {
				sb.append(cloudlet.getCloudletId()).append(",");
				sb.append(cloudlet.getGuestId()).append(",");
				sb.append(cloudlet.getUserId()).append(",");
				sb.append(cloudlet.getStatus()).append(",");
				sb.append(cloudlet.getResourceId()).append(",");
				sb.append(dft.format(cloudlet.getSubmissionTime())).append(",");
				sb.append(dft.format(cloudlet.getExecStartTime())).append(",");
				sb.append(dft.format(cloudlet.getExecFinishTime())).append(",");
				sb.append(cloudlet.getCloudletLength()).append(",");
				sb.append(dft.format(cloudlet.getProcessingCost())).append(",");
				sb.append(cloudlet.getCloudletFileSize()).append(",");
				sb.append(cloudlet.getUtilizationModelCpu().getClass().getSimpleName()).append(",");
				sb.append(cloudlet.getUtilizationModelRam().getClass().getSimpleName()).append(",");
				sb.append(cloudlet.getUtilizationModelBw().getClass().getSimpleName()).append(",");
				sb.append(dft.format(cloudlet.getWaitingTime())).append("\n");
		}
	
		try (FileWriter writer = new FileWriter(filePath)) {
			writer.write(sb.toString());
			System.out.println("Cloudlet results saved to " + filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void writeVmListToCSV(List<Vm> list, String filePath) {
    StringBuilder sb = new StringBuilder();

    sb.append("VM ID,User ID,MIPS,PEs,RAM,BW,Size,CloudletScheduler\n");

    for (Vm vm : list) {
        sb.append(vm.getId()).append(",");
        sb.append(vm.getUserId()).append(",");
        sb.append(vm.getMips()).append(",");
        sb.append(vm.getNumberOfPes()).append(",");
        sb.append(vm.getRam()).append(",");
        sb.append(vm.getBw()).append(",");
        sb.append(vm.getSize()).append("\n");
    }

    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(sb.toString());
        System.out.println("VM results saved to " + filePath);
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

	public static void writeHostListToCSV(List<Host> list, String filePath) {
    StringBuilder sb = new StringBuilder();

    sb.append("Host ID,PEs,MIPS,Total RAM,Total BW,Storage\n");

    for (Host host : list) {
        sb.append(host.getId()).append(",");
        sb.append(host.getNumberOfPes()).append(",");
        sb.append(host.getTotalMips()).append(",");
        sb.append(host.getRam()).append(",");
        sb.append(host.getBw()).append(",");
        sb.append(host.getStorage()).append("\n");
    }

    try (FileWriter writer = new FileWriter(filePath)) {
        writer.write(sb.toString());
        System.out.println("Host results saved to " + filePath);
    } catch (IOException e) {
        e.printStackTrace();
    }
	}

}
