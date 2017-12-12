package org.launchcode.models.data;

import org.launchcode.models.*;

import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
public class JobData {

    private ArrayList<Job> jobs = new ArrayList<>();
    private static JobData instance;

    private static JobFieldData<Employer> employers = new JobFieldData<>();
    private static JobFieldData<Location> locations = new JobFieldData<>();
    private static JobFieldData<CoreCompetency> coreCompetencies = new JobFieldData<>();
    private static JobFieldData<PositionType> positionTypes = new JobFieldData<>();


    private JobData() {
        JobDataImporter.loadData(this);
    }


    public static JobData getInstance() {
        if (instance == null) {
            instance = new JobData();
        }
        return instance;
    }

    public Job findById(int id) {
        for (Job job : jobs) {
            if (job.getId() == id)
                return job;
        }

        return null;
    }

    public ArrayList<Job> findAll() {
        return jobs;
    }


    public ArrayList<Job> findByColumnAndValue(JobFieldType column, String value) {

        ArrayList<Job> matchingJobs = new ArrayList<>();

        for (Job job : jobs) {
            if (getFieldByType(job, column).contains(value))
                matchingJobs.add(job);
        }

        return matchingJobs;
    }


    public ArrayList<Job> findByValue(String value) {

        ArrayList<Job> matchingJobs = new ArrayList<>();

        for (Job job : jobs) {

            if (job.getName().toLowerCase().contains(value)) {
                matchingJobs.add(job);
                continue;
            }

            for (JobFieldType column : JobFieldType.values()) {
                if (column != JobFieldType.ALL && getFieldByType(job, column).contains(value)) {
                    matchingJobs.add(job);
                    break;
                }
            }
        }

        return matchingJobs;
    }


    public void add(Job job) {
        jobs.add(job);
    }


    private static JobField getFieldByType(Job job, JobFieldType type) {
        switch(type) {
            case EMPLOYER:
                return job.getEmployer();
            case LOCATION:
                return job.getLocation();
            case CORE_COMPETENCY:
                return job.getCoreCompetency();
            case POSITION_TYPE:
                return job.getPositionType();
        }

        throw new IllegalArgumentException("Cannot get field of type " + type);
    }

    //Homemade setters byID instead of some randomness
    public void setEmployerById(Job job, int id){
        job.setEmployer(employers.findById(id));
    }

    public void setLocationById(Job job, int id){
        job.setLocation(locations.findById(id));
    }

    public void setCoreCompetencyById(Job job, int id){
        job.setCoreCompetency(coreCompetencies.findById(id));
    }

    public void setPositionTypeById(Job job, int id){
        job.setPositionType(positionTypes.findById(id));
    }

    public JobFieldData<Employer> getEmployers() {
        return employers;
    }

    public JobFieldData<Location> getLocations() {
        return locations;
    }

    public JobFieldData<CoreCompetency> getCoreCompetencies() {
        return coreCompetencies;
    }

    public JobFieldData<PositionType> getPositionTypes() {
        return positionTypes;
    }
}
