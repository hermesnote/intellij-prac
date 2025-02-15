public void createCommentWithMentionAndLineBreaks(String issueKey, String commentBody, String mentionedUser) {
    String url = JIRA_API_URL + "/rest/api/2/issue/" + issueKey + "/comment";
    
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + jiraToken);
    headers.setContentType(MediaType.APPLICATION_JSON);

    // 構造注釋內容，這裡使用 @mention 並且加入換行符
    String commentText = commentBody + "\n\n@" + mentionedUser + " Please take a look!\n\nAdditional information here.";

    Map<String, Object> commentData = new HashMap<>();
    commentData.put("body", commentText);

    // 設置 HttpEntity
    HttpEntity<Map<String, Object>> entity = new HttpEntity<>(commentData, headers);

    // 發送請求創建注釋
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

    if (response.getStatusCode() == HttpStatus.CREATED) {
        System.out.println("Comment created successfully with mention and line breaks.");
    } else {
        System.out.println("Failed to create comment: " + response.getBody());
    }
}
