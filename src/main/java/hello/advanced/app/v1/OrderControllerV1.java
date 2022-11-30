package hello.advanced.app.v1;

import hello.advanced.app.v2.OrderServiceV2;
import hello.advanced.trace.HelloTraceV1.HelloTraceV2;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderControllerV1 {
    private final OrderServiceV1 orderServiceV2;
    private final HelloTraceV2 trace;

    @GetMapping("/v2/request")
    public String request(String itemId) {
        TraceStatus status =  null;
        try {
            status = trace.begin("OrderController.request()");
            orderServiceV2.orderItem(status.getTraceId(),itemId);
            trace.end(status);
            return "ok";
        } catch (Exception e) {
          trace.exception(status, e);
          // 예외를 다시 던져 줘야 한다.
          throw e;
        }
    }
}
