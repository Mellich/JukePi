NETWORK_SRC_FILES= \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/SeekNotificationListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/PauseResumeNotificationListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/DebugNotificationListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/DefaultNotificationListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/GapListNotificationListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/listener/ResponseListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/ServerConnectionFactory.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/ServerAddress.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/exceptions/NotSupportedByNetworkException.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/exceptions/UDPTimeoutException.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/exceptions/PermissionDeniedException.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/exceptions/InputExecutionFailedException.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/exceptions/NotConnectedException.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/ServerConnection.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/ServerConnectionNotifier.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/impl/YTJBServerConnection.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/ExecutionThread.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/ResponseControllerImpl.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/YTJBLowLevelServerConnection.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/LowLevelServerConnection.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/InputListener.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/handler/ResponseHandler.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/handler/NotificationHandler.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/handler/InputHandler.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/functionality/ResponseController.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/serverconnection/Song.java \
$(NETWORK_DIR)$(INPUT_DIR)/client/LoadGapListStatus.java \
$(NETWORK_DIR)$(INPUT_DIR)/messages/ParseStatus.java \
$(NETWORK_DIR)$(INPUT_DIR)/messages/Permission.java \
$(NETWORK_DIR)$(INPUT_DIR)/messages/MessageType.java

NETWORK_CLASS_FILES= \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/SeekNotificationListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/PauseResumeNotificationListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/DebugNotificationListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/DefaultNotificationListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/GapListNotificationListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/listener/ResponseListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/ServerConnectionFactory.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/ServerAddress.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/exceptions/NotSupportedByNetworkException.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/exceptions/UDPTimeoutException.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/exceptions/PermissionDeniedException.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/exceptions/InputExecutionFailedException.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/exceptions/NotConnectedException.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/ServerConnection.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/ServerConnectionNotifier.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/impl/YTJBServerConnection.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/ExecutionThread.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/ResponseControllerImpl.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/YTJBLowLevelServerConnection.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/LowLevelServerConnection.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/InputListener.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/handler/ResponseHandler.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/handler/NotificationHandler.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/handler/InputHandler.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/functionality/ResponseController.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/serverconnection/Song.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/client/LoadGapListStatus.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/messages/ParseStatus.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/messages/Permission.class \
$(NETWORK_DIR)$(OUTPUT_DIR)/messages/MessageType.class

$(NETWORK_DIR)$(OUTPUT_DIR)/%.class: $(NETWORK_DIR)$(INPUT_DIR)/%.java
	@echo "Making JukePi Network..."
	$(MD) $(NETWORK_DIR)$(OUTPUT_DIR)
	$(JC) $(JFLAGS) -d $(NETWORK_DIR)$(OUTPUT_DIR) $(NETWORK_SRC_FILES)
