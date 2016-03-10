SET group_concat_max_len = 1000000;
SELECT term_id, GROUP_CONCAT(DISTINCT document_id SEPARATOR ',') as doc_ids
FROM terms_documents 
WHERE document_id < 13
GROUP BY term_id