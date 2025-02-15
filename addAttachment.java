public String uploadAttachment(String issueKey, File attachmentFile) throws IOException {
    String url = JIRA_API_URL + "/rest/api/2/issue/" + issueKey + "/attachments";
    
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer " + jiraToken);
    headers.set("X-Atlassian-Token", "no-check");  // 防止 Jira 鎖定附件上傳

    // 設置 multipart/form-data
    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("file", new FileSystemResource(attachmentFile));

    HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
    
    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

    // 假設 Jira API 返回附件的 ID
    if (response.getStatusCode() == HttpStatus.OK) {
        Map<String, Object> responseBody = response.getBody();
        return (String) responseBody.get("id");  // 返回附件 ID
    }

    throw new RuntimeException("Attachment upload failed");
}
