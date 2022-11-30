package hello.advanced.app.v1;

import hello.advanced.trace.HelloTraceV1.HelloTraceV2;
import hello.advanced.trace.TraceId;
import hello.advanced.trace.TraceStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryV1 {
    private final HelloTraceV2 trace;

    public void save(TraceId traceId, String itemId) {

        TraceStatus status =  null;
        try {
            status = trace.beginSync(traceId, "OrderServiceV1.save()");

            if (itemId.equals("ex")) {
                throw new IllegalStateException("예외 발생!");
            }
            sleep(1000);

            trace.end(status);
        } catch (Exception e) {
            trace.exception(status, e);
            // 예외를 다시 던져 줘야 한다.
            throw e;
        }

 }

    private void sleep(int mills) {
        try {
            Thread.sleep(mills);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
