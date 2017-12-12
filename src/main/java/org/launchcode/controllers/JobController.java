package org.launchcode.controllers;

import org.launchcode.models.Employer;
import org.launchcode.models.Job;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

import static org.launchcode.models.JobFieldType.EMPLOYER;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,  @RequestParam int id) {

        // THINKDONE TODO #1 - get the Job with the given ID and pass it into the view
        // THINK DONE!
        Job requestedJob = jobData.findById(id);
        model.addAttribute("job", requestedJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if (!errors.hasErrors()) {
            Job createdJob = new Job();
            createdJob.setName(jobForm.getName());
            jobData.setEmployerById(createdJob, jobForm.getEmployerId());
            jobData.setLocationById(createdJob, jobForm.getLocationId());
            jobData.setCoreCompetencyById(createdJob, jobForm.getCoreCompetencyId());
            jobData.setPositionTypeById(createdJob, jobForm.getPositionTypeId());

            jobData.add(createdJob);
            return "redirect:?id=" + createdJob.getId();
        }

        model.addAttribute(new JobForm());
        model.addAttribute("message", "Name can't be empty.");
        return "new-job";

    }
}
