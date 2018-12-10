package mandatory.two.helper;

import mandatory.two.model.Admin;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;
import mandatory.two.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

//naskd
public class SessionHelper {

    public static boolean isSessionValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //System.out.println(session.getAttribute("user"));
        return session.getAttribute("user") != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user") instanceof Admin) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isCompany(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user") instanceof Company) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean isCustomer(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user") instanceof Customer) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    /*
    public static String loginRedirect(User user){
        if(user instanceof Company){
            return "redirect:/company/index";
        } else if(user instanceof Admin){
            return "redirect:/admin/index";
        } else if(user instanceof Customer){
            return "redirect:/customer/index";
        } else{
            return "redirect:/login";
        }
    }*/
    /*
    public static String redirectAdministrator(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isAdministrator(request)) {
                return "administrator/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }

    public static String redirectTeacher(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isTeacher(request)) {
                return "teacher/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }
    public static String redirectStudent(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isStudent(request)) {
                return "student/"+ view;
            } else {
                return "all/forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }

    public static void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }*/
}
