package com.universityProject.workLoad.secvices;

import com.universityProject.workLoad.model.*;
import com.universityProject.workLoad.repositories.GroupOfStudentsRepository;
import com.universityProject.workLoad.repositories.ScheduleRepository;
import com.universityProject.workLoad.repositories.SubGroupOfStudentsRepository;
import com.universityProject.workLoad.repositories.TeacherRepository;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TeacherService teacherService;
    private final EducationDisciplineService educationDisciplineService;
    private final GroupOfStudentsService groupOfStudentsService;
    private final SubGroupOfStudentsService subGroupOfStudentsService;
    private final SubGroupOfStudentsRepository groupOfStudentsRepository;
    private final SubGroupOfStudentsRepository subGroupOfStudentsRepository;

    @Autowired
    public ScheduleService(ScheduleRepository scheduleRepository, TeacherService teacherService, EducationDisciplineService educationDisciplineService, GroupOfStudentsService groupOfStudentsService, SubGroupOfStudentsService subGroupOfStudentsService, SubGroupOfStudentsRepository groupOfStudentsRepository, SubGroupOfStudentsRepository subGroupOfStudentsRepository) {
        this.scheduleRepository = scheduleRepository;
        this.teacherService = teacherService;
        this.educationDisciplineService = educationDisciplineService;
        this.groupOfStudentsService = groupOfStudentsService;
        this.subGroupOfStudentsService = subGroupOfStudentsService;

        this.groupOfStudentsRepository = groupOfStudentsRepository;
        this.subGroupOfStudentsRepository = subGroupOfStudentsRepository;
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }
    public Schedule findById(int id) {
        Optional<Schedule> schedule = scheduleRepository.findById(id);
        return schedule.orElse(null);
    }


    @Transactional
    public void save(Schedule schedule, GroupOfStudents groupOfStudentsInfo) {

        System.out.println("Бьтмлвытмвтымтвыдмвылд");

        schedule.getActualTeacher().addActualWorkingHours(schedule.getEducationalDiscipline().getEducationalDisciplineDuration());

        Integer studentLimit = schedule.getEducationalDiscipline().getMaxStudents();

        GroupOfStudents groupOfStudents = groupOfStudentsService.findById(groupOfStudentsInfo.getGroupId());

        if( studentLimit == null ||  studentLimit >= groupOfStudents.getNumberOfStudents() ){


            SubGroupOfStudents subGroup = createSubGroup(groupOfStudents);
            subGroup.addSchedule(schedule);
            schedule.setSubGroupOfStudents(subGroup);
            subGroupOfStudentsRepository.save(subGroup);
            scheduleRepository.save(schedule);

        }else {

            List<SubGroupOfStudents> subGroupsOfStudents = createSubGroups(groupOfStudents, studentLimit);



            for(int i = 0; i < subGroupsOfStudents.size(); i++){

                Schedule scheduleToSave = (i==0) ? new Schedule(subGroupsOfStudents.get(i),schedule.getEducationalDiscipline(),null)
                : new Schedule(subGroupsOfStudents.get(i),schedule.getEducationalDiscipline(),schedule.getActualTeacher());
                subGroupsOfStudents.get(i).addSchedule(scheduleToSave);
                subGroupOfStudentsRepository.save(subGroupsOfStudents.get(i));
                scheduleRepository.save(scheduleToSave);
            }
        }
    }

    @Transactional
    public void update(int id, Schedule updatedSchedule){

        Schedule scheduleToUpdate = findById(id);
        if(scheduleToUpdate.getActualTeacher() != null){
            scheduleToUpdate.getActualTeacher().subtractActualWorkingHours(scheduleToUpdate.
                    getEducationalDiscipline().getEducationalDisciplineDuration());
        }


        updatedSchedule.setScheduleId(scheduleToUpdate.getScheduleId());
        updatedSchedule.setSubGroupOfStudents(scheduleToUpdate.getSubGroupOfStudents());
        updatedSchedule.setEducationalDiscipline(scheduleToUpdate.getEducationalDiscipline());
        updatedSchedule.getActualTeacher().addActualWorkingHours(updatedSchedule.
                getEducationalDiscipline().getEducationalDisciplineDuration());

        scheduleRepository.save(updatedSchedule);
    }



    private SubGroupOfStudents createSubGroup(GroupOfStudents groupOfStudents){

        String nameOfSubgroup = groupOfStudents.getSpeciality() + "."
                + groupOfStudents.getYearOfEntering() + "."
                + "Full";

        Optional<SubGroupOfStudents> subGroupOfStudents = subGroupOfStudentsService.findByName(nameOfSubgroup);

        if(subGroupOfStudents.isPresent()){
            return subGroupOfStudents.get();
        }else {
            SubGroupOfStudents subGroupOfStudentsPresent = new SubGroupOfStudents(nameOfSubgroup, groupOfStudents, groupOfStudents.getNumberOfStudents());
            return subGroupOfStudentsPresent;
        }

    }

    private List<SubGroupOfStudents> createSubGroups(GroupOfStudents groupOfStudents,int studentLimit ){

        List<SubGroupOfStudents> subGroupOfStudentsList = new ArrayList<>();

        int numberOfSubGroups = (int)Math.ceil((double) groupOfStudents.getNumberOfStudents() / (double) studentLimit);
        int numberOfStudentsInGroups = groupOfStudents.getNumberOfStudents() / (int) numberOfSubGroups;
        int numberToAddStudents = (int) (((groupOfStudents.getNumberOfStudents() /(double) numberOfSubGroups) - numberOfStudentsInGroups) * numberOfSubGroups);

        System.out.println("( " + groupOfStudents.getNumberOfStudents() + " / " + numberOfSubGroups + " ) " +
                " - " + numberOfStudentsInGroups + " ) " +  " * " + numberOfSubGroups);

        for (int i = 0; i != numberOfSubGroups; i++) {

            String nameOfSubgroup = groupOfStudents.getSpeciality() + "."
                    + groupOfStudents.getYearOfEntering() + "."
                    + studentLimit + "."
                    + i;

            Optional<SubGroupOfStudents> subGroupOfStudents = subGroupOfStudentsService.findByName(nameOfSubgroup);

            if(subGroupOfStudents.isPresent()){
                subGroupOfStudentsList.add(subGroupOfStudents.get());

            }else {

                System.out.println(numberToAddStudents);

                int numberOfStudents;

                if (numberToAddStudents > 0) {
                    numberOfStudents =  numberOfStudentsInGroups + 1;
                    numberToAddStudents--;
                } else {
                    numberOfStudents = numberOfStudentsInGroups;
                }

                subGroupOfStudentsList.add(new SubGroupOfStudents(nameOfSubgroup, groupOfStudents, numberOfStudents));
            }

        }

        return subGroupOfStudentsList;

    }






    @Transactional
    public void delete(int id){

        scheduleRepository.findById(id).get().getActualTeacher()
                .subtractActualWorkingHours(scheduleRepository
                        .findById(id).get().getEducationalDiscipline()
                        .getEducationalDisciplineDuration());

        scheduleRepository.deleteById(id);
    }




}
