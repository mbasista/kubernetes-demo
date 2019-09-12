{{- define "service.ns" }}
apiVersion: v1
kind: Service
metadata:
  name: {{ $.Values.application.name }}
  labels:
    app: {{ $.Values.application.name }}
spec:
  type: ClusterIP
  ports:
    - port: {{ $.Values.application.port }}
      name: {{ $.Values.application.name }}
  selector:
    app: {{ $.Values.application.name }}
{{- end }}