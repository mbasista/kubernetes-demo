{{- define "deployment.ns" }}
apiVersion: apps/v1
kind: Deployment
metadata:
  name: {{ $.Values.application.name }}
  labels:
    app: {{ $.Values.application.name }}
spec:
  selector:
    matchLabels:
      app: {{ $.Values.application.name }}
  replicas: {{ $.Values.deployment.replicas }}
  template:
    metadata:
      labels:
        app: {{ $.Values.application.name }}
    spec:
      restartPolicy: {{ $.Values.deployment.restartPolicy }}
      containers:
        - name: {{ $.Values.application.name }}
          image: {{ $.Values.image.name }}:{{ $.Values.image.tag }}
          ports:
            - containerPort: {{ $.Values.application.port }}
              name: {{ $.Values.application.name }}
          envFrom:
          - configMapRef:
              name: {{ $.Values.application.name }}-config
          resources:
            requests:
              cpu: {{ $.Values.deployment.resources.requests.cpu }}
            limits:
              cpu: {{ $.Values.deployment.resources.limits.cpu }}
          readinessProbe:
            httpGet:
              path: /healthz
              port: {{ $.Values.application.port }}
              initialDelaySeconds: 45
              periodSeconds: 10
{{- end }}