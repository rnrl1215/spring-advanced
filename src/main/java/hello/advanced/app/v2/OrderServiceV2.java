package hello.advanced.app.v2;

import hello.advanced.trace.HelloTraceV1.HelloTraceV1;
import hello.advanced.trace.HelloTraceV1.HelloTraceV2;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceV2 {
    private final OrderRepositoryV2 orderRepository;
    private final HelloTraceV2 trace;

    public void orderItem(String itemId) {

        TraceStatus status =  null;
        try {
            status = trace.begin("OrderServiceV1.orderItem()");
            orderRepository.save(itemId);
            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            // 예외를 다시 던져 줘야 한다.
            throw e;
        }

    }
}
