/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import Domain.CalendarModule.Activity;

/**
 *
 * @author morte
 */
public class ActivityManager {

    private static String place;
    private static String description;
    private static String type;
    private static String startDate;
    private static String endDate;
    private static Boolean shared;
    private static Boolean entry;
    private static String title;
    private static int activityID;

    private static int residentID;
    
    
    private static void getActivityInfo(Activity activity){
        place = activity.getPlace();
        description = activity.getDescription();
        type = activity.getType();
        startDate = activity.getStartDate().toString();
        endDate = activity.getEndDate().toString();
        shared = activity.getShared();
        entry = activity.getEntry();
        title = activity.getTitle();
        activityID= activity.getActivityID();
    }
    
    private static void getResidentID (){
        residentID = UserManager.getCurrentResident().getID();
    }
    
    public static void storeActivity (Activity activity){
        ActivityManager.getActivityInfo(activity);
        ActivityManager.getResidentID();
        if(ActivityRepository.storeActivity(place, description, type, startDate, endDate, shared, entry, title, activityID, residentID)){
            System.out.println("successful storing");
        } else {
            System.out.println("unsuccessful storing");
        }
        
    }
    
    
}
