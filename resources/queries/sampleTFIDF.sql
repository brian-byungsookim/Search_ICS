SELECT B.term_id, GROUP_CONCAT("{", A.document_id, ':', A.tf * B.idf,"}") as TFIDF
FROM tf A, idf B
WHERE A.term_id = B.term_id
GROUP BY B.term_id;