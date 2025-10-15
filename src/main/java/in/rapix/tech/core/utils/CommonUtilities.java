package in.rapix.tech.core.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CommonUtilities {

    public static Logger LOG = LogManager.getLogger(CommonUtilities.class);

    public static void logRequest(String method, String serverUrl, String body) {
        LOG.info(method);
        LOG.info(serverUrl);
        LOG.info(body);
    }

    public static List<Integer> getErrorCodes() {
        String[] statuses = PropertyFileReader.getProperty("STATUS_CODES").split(",");
        List<Integer> statusCodes = Arrays.stream(statuses)
                .map(status -> Integer.parseInt(status.trim()))
                .collect(Collectors.toList());
        return statusCodes;
    }
}
