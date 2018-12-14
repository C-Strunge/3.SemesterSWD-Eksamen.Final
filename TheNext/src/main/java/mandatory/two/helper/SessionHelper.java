package mandatory.two.helper;

import mandatory.two.model.Admin;
import mandatory.two.model.Company;
import mandatory.two.model.Customer;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SessionHelper {

    public static boolean isSessionValid(HttpServletRequest request) {
        HttpSession session = request.getSession();
        //System.out.println(session.getAttribute("user"));
        return session.getAttribute("admin") != null || session.getAttribute("company") != null || session.getAttribute("customer") != null;
    }

    public static boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();

        if (session.getAttribute("admin") != null) {
            if (session.getAttribute("admin") instanceof Admin) {
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

        if (session.getAttribute("company") != null) {
            if (session.getAttribute("company") instanceof Company) {
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

        if (session.getAttribute("customer") != null) {
            if (session.getAttribute("customer") instanceof Customer) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }


    public static String redirectAdministrator(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isAdmin(request)) {
                return view;
            } else {
                return "forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }

    public static String redirectCustomer(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isCustomer(request)) {
                return view;
            } else {
                return "forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }
    public static String redirectCompany(HttpServletRequest request, String view) {
        if (isSessionValid(request)) {
            if (isCompany(request)) {
                return view;
            } else {
                return "forbidden";
            }
        } else{
            return "redirect:/login";
        }
    }

    public static void logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.invalidate();
    }
}
