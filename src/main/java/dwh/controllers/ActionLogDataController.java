package dwh.controllers;

import dwh.adapters.ActionLogDataAdapter;
import dwh.adapters.ActionLogDataAdapterImpl;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ActionLogDataController {

    private final ActionLogDataAdapter actionLogDataAdapter;

    public ActionLogDataController() {
        actionLogDataAdapter = new ActionLogDataAdapterImpl();
    }
}
