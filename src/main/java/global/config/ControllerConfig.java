package global.config;

import controller.AppController;
import controller.AuthHandler;
import controller.ControllerContext;
import controller.ExitController;
import controller.MeetingController;
import controller.MemberController;
import controller.RemindHandler;
import java.util.List;

public class ControllerConfig {
    private final MemberController memberController;
    private final MeetingController meetingController;
    private final ExitController exitController;

    public ControllerConfig(
            ServiceConfig serviceConfig,
            ControllerContext controllerContext,
            RepositoryConfig repositoryConfig
    ) {

        this.memberController = new MemberController(
                serviceConfig.getMemberService()
                , controllerContext
        );
        this.meetingController = new MeetingController(
                serviceConfig.getMeetingService(),
                serviceConfig.getAttendanceService(),
                controllerContext
        );
        this.exitController = new ExitController(
                controllerContext,
                repositoryConfig.getParticipantRepository(),
                repositoryConfig.getMemberRepository(),
                repositoryConfig.getMeetingRepository()
        );
    }

    public AuthHandler getAuthHandler() {
        return this.memberController;
    }

    public RemindHandler getRemindHandler() {
        return this.meetingController;
    }

    public ExitController getExitController() {
        return exitController;
    }

    public List<AppController> getAllControllers() {
        return List.of(
                meetingController,
                memberController,
                exitController
        );
    }

}
