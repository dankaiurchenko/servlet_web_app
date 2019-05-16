package com.danarossa.controllers;

import com.danarossa.database.AbstractDaoFactory;
import com.google.gson.Gson;

class ParentController {
    AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getDaoFactory();
    Gson gson = new Gson();
}
