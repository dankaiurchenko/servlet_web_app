package com.danarossa.controllers;

import com.danarossa.database.daointerfaces.UserDao;
import com.danarossa.dto.Account;
import com.danarossa.entities.User;
import com.danarossa.router.Controller;
import com.danarossa.router.Url;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SecureRandom;
import java.util.Arrays;

@Controller("/auth")
public class AuthorisationController extends ParentController {

    public void login(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into login");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            Account account = gson.fromJson(body, Account.class);
            System.out.println("account     " + account);
            User userByEmail = userDao.getUserByEmailAndPass(account.getEmail(), account.getPassword());
            if (userByEmail != null) {
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[20];
                random.nextBytes(bytes);
                String token = String.valueOf(bytes);
                userByEmail.setToken(token);
                userDao.update(userByEmail);
                userByEmail.setPassword("");
                writeToResponseBody(userByEmail, response);
            } else throw new ControllerException("User not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }


    public void register(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into register");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            User newUser = gson.fromJson(body, User.class);
            User userByEmail = userDao.getUserByEmail(newUser.getEmail());
            if (userByEmail == null) {
                SecureRandom random = new SecureRandom();
                byte[] bytes = new byte[20];
                random.nextBytes(bytes);
                String token = Arrays.toString(bytes);
                newUser.setToken(token);
                userDao.insert(newUser);
                newUser.setPassword("");
                writeToResponseBody(newUser, response);
            } else throw new ControllerException("User not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }


    public User getUser(HttpServletRequest request) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            User userByToken = userDao.getUserByToken(request.getHeader("Authorization"));
            if (userByToken == null)
                throw new ControllerException("Token expired");
            return userByToken;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }


    @Url("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        try (UserDao userDao = abstractDaoFactory.getUserDao()) {
            System.out.println("into logout");
            String body = getBody(request);
            System.out.println("user from front    " + body);
            User user = userDao.getUserByToken(gson.fromJson(body, User.class).getToken());
            user.setToken("");
            userDao.update(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ControllerException("Error");
        }
    }
}
