package com.nvlad.tinypng.diagnostic;

import com.intellij.diagnostic.AbstractMessage;
import com.intellij.openapi.diagnostic.IdeaLoggingEvent;
import com.intellij.openapi.diagnostic.SubmittedReportInfo;
import com.intellij.util.Consumer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.Component;

public class ErrorReportHandler extends com.intellij.openapi.diagnostic.ErrorReportSubmitter {
    @Override
    public String getReportActionText() {
        return "Report to Plugin Developer";
    }

    @Override
    public boolean submit(
            IdeaLoggingEvent[] events,
            @Nullable String additionalInfo,
            @NotNull Component parentComponent,
            @NotNull Consumer<? super SubmittedReportInfo> consumer
    ) {
        for (IdeaLoggingEvent event : events) {
            Throwable throwable = event.getThrowable();
            if (event.getData() instanceof AbstractMessage) {
                throwable = ((AbstractMessage) event.getData()).getThrowable();
            }

            SentryErrorReporter.submitErrorReport(throwable, additionalInfo, consumer);
        }

        return true;
    }
}
