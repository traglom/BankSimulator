package com.ujm.xmltech;

import java.io.File;
import java.util.Calendar;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.ujm.xmltech.utils.BankSimulationConstants;

public class App {

    //TP scheduler
    public void launch() {
        File input = retrieveFileToProcess();
        if (input != null) {
            String[] springConfig = { "spring/batch/jobs/jobs.xml" };
            ApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
            JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher");
            Job job = (Job) context.getBean("integratePain008File");
            try {
                JobExecution execution = jobLauncher.run(job, new JobParametersBuilder().addString("inputFile", input.getName()).toJobParameters());
                System.out.println("Exit Status : " + execution.getStatus());
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[" + Calendar.getInstance().getTime().toString() + "] No file to process");
        }
    }

    private File retrieveFileToProcess() {
        File toReturn = null;
        File folder = new File(BankSimulationConstants.IN_DIRECTORY);
        for (File file : folder.listFiles()) {
            System.out.println("File found : " + file.getName());
            toReturn = file;
        }
        return toReturn;
    }

    /*
     * public void main() { //TP JAXB Pain008Reader reader = new Pain008Reader(); Pain008Writer writer = new Pain008Writer(); Pain008Checker checker = new Pain008Checker(); try { checker.checkFile(); Object item = reader.read(); writer.write(item); } catch (Exception e) { e.printStackTrace(); } //TP persistence service.createTransaction(); //TP Spring Batch new App().run(); } private void run() { TimerTask task = new MyScheduler(); Timer timer = new Timer(); //launch the job after 1 second the
     * first time and then every 10 second timer.schedule(task, 1000, 10000); } public class MyScheduler extends TimerTask {
     * @Override public void run() { //load configuration String[] springConfig = { "spring/batch/jobs/jobs.xml" }; ApplicationContext context = new ClassPathXmlApplicationContext(springConfig); JobLauncher jobLauncher = (JobLauncher) context.getBean("jobLauncher"); Job job = (Job) context.getBean("integratePain008File"); //launch job try { JobExecution execution = jobLauncher.run(job, new JobParameters()); System.out.println("Exit Status : " + execution.getStatus()); } catch (Exception e) {
     * e.printStackTrace(); } System.out.println("Done"); } }
     */
}
